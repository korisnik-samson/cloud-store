package com.samson.cloudstore.services;

import com.samson.cloudstore.config.RabbitMQConfig;
import com.samson.cloudstore.dto.FileUploadedMessage;
import com.samson.cloudstore.models.FileVersion;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.models.elasticsearch.StorageNodeDocument;
import com.samson.cloudstore.repositories.FileVersionRepository;
import com.samson.cloudstore.repositories.StorageNodeRepository;
import com.samson.cloudstore.repositories.elasticsearch.StorageNodeSearchRepository;
import com.samson.cloudstore.utilities.NodeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageNodeService {

    private final StorageNodeRepository storageNodeRepository;
    private final FileVersionRepository fileVersionRepository;
    private final ObjectStorageService objectStorageService;
    private final AuditService auditService;
    private final RabbitTemplate rabbitTemplate;
    private final StorageNodeSearchRepository searchRepository;

    // ------------------
    // Listing
    // ------------------

    @Cacheable(value = "nodes", key = "'children:' + #ownerId + ':' + (#parentId ?: 'root')")
    public List<StorageNode> listChildren(UUID ownerId, UUID parentId) {
        if (parentId == null) return storageNodeRepository.findByOwnerIdAndParentIsNullAndTrashedFalseOrderByTypeAscNameAsc(ownerId);

        return storageNodeRepository.findByOwnerIdAndParent_IdAndTrashedFalseOrderByTypeAscNameAsc(ownerId, parentId);
    }

    public List<StorageNode> search(UUID ownerId, String query) {
        if (query == null || query.isBlank()) return List.of();

        // Use Elasticsearch for search
        List<StorageNodeDocument> docs = searchRepository.findByNameAndOwnerIdAndTrashedFalse(query.trim(), ownerId);

        // Convert documents back to entities (best-effort from DB to get full metadata if needed, or just map from docs)
        // For simplicity, let's just return what we have in the index or fetch from DB by IDs
        List<UUID> ids = docs.stream().map(d -> UUID.fromString(d.getId())).toList();
        return storageNodeRepository.findAllById(ids);
    }

    @Cacheable(value = "nodes", key = "'trash:' + #ownerId")
    public List<StorageNode> listTrashRoots(UUID ownerId) {
        return storageNodeRepository.findTrashRoots(ownerId);
    }

    // ------------------
    // Create
    // ------------------

    @Transactional
    @CacheEvict(value = "nodes", key = "'children:' + #ownerId + ':' + (#parentId ?: 'root')")
    public StorageNode createFolder(UUID ownerId, UUID parentId, String name) {
        Objects.requireNonNull(ownerId, "ownerId");

        if (name == null || name.isBlank()) throw new IllegalArgumentException("Folder name required");

        StorageNode parent = resolveFolderParent(ownerId, parentId);
        ensureNoNameConflict(ownerId, parentId, name);

        OffsetDateTime now = OffsetDateTime.now();

        StorageNode node = StorageNode.builder()
                .ownerId(ownerId)
                .parent(parent)
                .name(name)
                .type(NodeType.FOLDER)
                .path(buildPath(parent, name))
                .trashed(false)
                .createdAt(now)
                .updatedAt(now)
                .version(0)
                .build();

        StorageNode saved = storageNodeRepository.save(node);
        auditService.log(ownerId, "NODE_CREATE_FOLDER", saved.getId(), json("name", name));

        // Index in Elasticsearch
        indexNode(saved);

        return saved;
    }

    /** Convenience overload for existing controllers. */
    @Transactional
    public StorageNode saveUploadedFileMetadata(@NonNull Users user, UUID parentId, String fileName, String mimeType,
                                                long sizeBytes, String objectKey, String checksumSha256) {
        return saveUploadedFileMetadata(user.getUserId(), parentId, fileName, mimeType, sizeBytes, objectKey, checksumSha256);
    }


    /**
     * Called when an object has been uploaded to MinIO using a presigned URL.
     * If a file with the same name exists in the same folder, we create a new version.
     */
    @Transactional
    @CacheEvict(value = "nodes", key = "'children:' + #ownerId + ':' + (#parentId ?: 'root')")
    public StorageNode saveUploadedFileMetadata(UUID ownerId,
                                                UUID parentId,
                                                String fileName,
                                                String mimeType,
                                                long sizeBytes,
                                                String objectKey,
                                                String checksumSha256) {

        Objects.requireNonNull(ownerId, "ownerId");
        if (fileName == null || fileName.isBlank()) throw new IllegalArgumentException("File name required");
        if (objectKey == null || objectKey.isBlank()) throw new IllegalArgumentException("objectKey required");

        StorageNode parent = resolveFolderParent(ownerId, parentId);
        OffsetDateTime now = OffsetDateTime.now();

        // Overwrite-as-new-version for same-name file
        Optional<StorageNode> existingOpt = findSiblingByName(ownerId, parentId, fileName);
        if (existingOpt.isPresent()) {
            StorageNode existing = existingOpt.get();
            if (existing.getType() != NodeType.FILE) {
                throw new IllegalArgumentException("A folder with that name already exists");
            }

            // create version snapshot of existing
            int nextVersion = fileVersionRepository.findMaxVersionNo(existing.getId()) + 1;
            FileVersion version = FileVersion.builder()
                    .node(existing)
                    .versionNo(nextVersion)
                    .objectKey(existing.getObjectKey())
                    .sizeBytes(existing.getSizeBytes() == null ? 0L : existing.getSizeBytes())
                    .mimeType(existing.getMimeType())
                    .checksumSha256(existing.getChecksumSha256())
                    .createdAt(now)
                    .build();
            fileVersionRepository.save(version);

            existing.setObjectKey(objectKey);
            existing.setMimeType(mimeType);
            existing.setSizeBytes(sizeBytes);
            existing.setChecksumSha256(checksumSha256);
            existing.setUpdatedAt(now);

            StorageNode saved = storageNodeRepository.save(existing);

            auditService.log(ownerId, "NODE_UPLOAD_VERSION", saved.getId(), json("version", String.valueOf(nextVersion)));
            
            // Update index
            indexNode(saved);
            
            return saved;
        }

        // new file
        StorageNode node = StorageNode.builder()
                .ownerId(ownerId)
                .parent(parent)
                .name(fileName)
                .type(NodeType.FILE)
                .path(buildPath(parent, fileName))
                .objectKey(objectKey)
                .sizeBytes(sizeBytes)
                .mimeType(mimeType)
                .checksumSha256(checksumSha256)
                .trashed(false)
                .createdAt(now)
                .updatedAt(now)
                .version(0)
                .build();

        StorageNode saved = storageNodeRepository.save(node);
        auditService.log(ownerId, "NODE_UPLOAD_CREATE", saved.getId(), null);

        publishUploadEvent(saved);
        
        // Index in Elasticsearch
        indexNode(saved);

        return saved;
    }

    private void indexNode(StorageNode node) {
        try {
            StorageNodeDocument doc = StorageNodeDocument.builder()
                    .id(node.getId().toString())
                    .ownerId(node.getOwnerId())
                    .name(node.getName())
                    .type(node.getType().name())
                    .mimeType(node.getMimeType())
                    .trashed(node.isTrashed())
                    .build();
            searchRepository.save(doc);
        } catch (Exception e) {
            log.error("Failed to index node {} in Elasticsearch", node.getId(), e);
        }
    }

    private void publishUploadEvent(@NonNull StorageNode node) {
        FileUploadedMessage message = new FileUploadedMessage(
                node.getId(),
                node.getOwnerId(),
                node.getName(),
                node.getMimeType(),
                node.getObjectKey(),
                node.getSizeBytes() != null ? node.getSizeBytes() : 0L
        );

        String routingKey = node.getMimeType() != null && node.getMimeType().startsWith("image/")
                ? "file.uploaded.image"
                : "file.uploaded.other";

        rabbitTemplate.convertAndSend(RabbitMQConfig.CLOUDSTORE_EXCHANGE, routingKey, message);
    }

    public List<FileVersion> listVersions(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));
        if (node.getType() != NodeType.FILE) throw new IllegalArgumentException("Only files have versions");
        return fileVersionRepository.findByNodeIdOrderByVersionNoDesc(nodeId);
    }

    // ------------------
    // Download
    // ------------------

    public String getDownloadUrl(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (node.isTrashed()) throw new IllegalArgumentException("Node is trashed");
        if (node.getType() != NodeType.FILE) throw new IllegalArgumentException("Only files can be downloaded");

        auditService.log(ownerId, "NODE_DOWNLOAD_URL", node.getId(), null);

        return objectStorageService.createPresignedGetUrl(node.getObjectKey());
    }

    // ------------------
    // Trash / Restore
    // ------------------

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "nodes", key = "'children:' + #ownerId + ':' + (#node.parent != null ? #node.parent.id : 'root')", beforeInvocation = true),
        @CacheEvict(value = "nodes", key = "'trash:' + #ownerId", beforeInvocation = true)
    })
    public void trash(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (node.isTrashed()) return;

        OffsetDateTime now = OffsetDateTime.now();
        markSubtreeTrashed(node, now);
        auditService.log(ownerId, "NODE_TRASH", nodeId, null);

        // Update search index: either mark as trashed or remove
        // For our findByName...AndTrashedFalse, marking as trashed is enough if we re-index the subtree
        updateSubtreeInIndex(node);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "nodes", key = "'children:' + #ownerId + ':root'", beforeInvocation = true),
        @CacheEvict(value = "nodes", key = "'trash:' + #ownerId", beforeInvocation = true)
    })
    public void restore(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (!node.isTrashed()) return;

        // if parent is trashed, restore to root
        StorageNode parent = node.getParent();
        if (parent != null && parent.isTrashed()) {
            node.setParent(null);
            node.setPath(buildPath(null, node.getName()));
        }

        untrashSubtree(node);
        updatePathsRecursively(node);
        auditService.log(ownerId, "NODE_RESTORE", nodeId, null);

        // Update search index
        updateSubtreeInIndex(node);
    }

    // Permanently delete a trashed node and its subtree
    @Transactional
    @CacheEvict(value = "nodes", key = "'trash:' + #ownerId")
    public void purge(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (!node.isTrashed()) throw new IllegalArgumentException("Node must be trashed before purge");

        List<StorageNode> subtree = collectSubtree(node);

        // Delete objects for current files
        for (StorageNode n : subtree) {
            if (n.getType() == NodeType.FILE && n.getObjectKey() != null) {
                try {
                    objectStorageService.deleteObject(n.getObjectKey());

                } catch (Exception ignored) {
                }
            }
            // Remove from search index
            removeFromIndex(n.getId());
        }

        // Delete version objects too
        for (StorageNode n : subtree) {
            if (n.getType() == NodeType.FILE) {
                List<FileVersion> versions = fileVersionRepository.findByNodeIdOrderByVersionNoDesc(n.getId());

                for (FileVersion v : versions) {
                    try {
                        objectStorageService.deleteObject(v.getObjectKey());

                    } catch (Exception ignored) { }
                }

                fileVersionRepository.deleteAll(versions);
            }
        }

        subtree.sort(Comparator.comparingInt(this::depth).reversed());
        storageNodeRepository.deleteAll(subtree);

        auditService.log(ownerId, "NODE_PURGE", nodeId, json("count", String.valueOf(subtree.size())));
    }

    // ------------------
    // Move / Rename
    // ------------------

    @Transactional
    @CacheEvict(value = "nodes", key = "'children:' + #ownerId + ':' + (#node.parent != null ? #node.parent.id : 'root')")
    public StorageNode rename(UUID ownerId, UUID nodeId, String newName) {
        if (newName == null || newName.isBlank()) throw new IllegalArgumentException("Name required");

        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));
        if (node.isTrashed()) throw new IllegalArgumentException("Cannot rename trashed node");

        UUID parentId = node.getParent() == null ? null : node.getParent().getId();
        if (!node.getName().equalsIgnoreCase(newName)) ensureNoNameConflict(ownerId, parentId, newName);


        node.setName(newName);
        node.setPath(buildPath(node.getParent(), newName));
        node.setUpdatedAt(OffsetDateTime.now());

        StorageNode saved = storageNodeRepository.save(node);
        updatePathsRecursively(saved);

        auditService.log(ownerId, "NODE_RENAME", nodeId, json("name", newName));

        // Update search index
        indexNode(saved);

        return saved;
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "nodes", key = "'children:' + #ownerId + ':' + (#node.parent != null ? #node.parent.id : 'root')"),
        @CacheEvict(value = "nodes", key = "'children:' + #ownerId + ':' + (#newParentId ?: 'root')")
    })
    public StorageNode move(UUID ownerId, UUID nodeId, UUID newParentId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));
        if (node.isTrashed()) throw new IllegalArgumentException("Cannot move trashed node");

        StorageNode newParent = resolveFolderParent(ownerId, newParentId);

        // cycle check for folders
        if (newParent != null && node.getType() == NodeType.FOLDER) {
            StorageNode cursor = newParent;

            while (cursor != null) {
                if (cursor.getId().equals(node.getId())) throw new IllegalArgumentException("Cannot move a folder into itself (cycle)");

                cursor = cursor.getParent();
            }
        }

        UUID parentId = newParent == null ? null : newParent.getId();
        ensureNoNameConflict(ownerId, parentId, node.getName());

        node.setParent(newParent);
        node.setPath(buildPath(newParent, node.getName()));
        node.setUpdatedAt(OffsetDateTime.now());

        StorageNode saved = storageNodeRepository.save(node);
        updatePathsRecursively(saved);

        auditService.log(ownerId, "NODE_MOVE", nodeId, json("newParentId", String.valueOf(newParentId)));

        // Update search index
        indexNode(saved);

        return saved;
    }

    private void removeFromIndex(UUID nodeId) {
        try {
            searchRepository.deleteById(nodeId.toString());
        } catch (Exception e) {
            log.error("Failed to remove node {} from Elasticsearch", nodeId, e);
        }
    }

    private void updateSubtreeInIndex(StorageNode root) {
        List<StorageNode> subtree = collectSubtree(root);
        for (StorageNode n : subtree) {
            indexNode(n);
        }
    }

    // ------------------
    // Stats
    // ------------------

    public Map<String, Object> getUserStats(UUID ownerId) {
        long usedBytes = storageNodeRepository.sumSizeByOwnerAndTypeNotTrashed(ownerId, NodeType.FILE);
        long activeCount = storageNodeRepository.countByOwnerIdAndTrashedFalse(ownerId);
        long trashCount = storageNodeRepository.countByOwnerIdAndTrashedTrue(ownerId);

        return Map.of(
                "usedBytes", usedBytes,
                "activeCount", activeCount,
                "trashCount", trashCount
        );
    }

    // ------------------
    // Helpers
    // ------------------

    private StorageNode resolveFolderParent(UUID ownerId, UUID parentId) {
        if (parentId == null) return null;

        StorageNode parent = storageNodeRepository.findByIdAndOwnerId(parentId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));
        if (parent.isTrashed()) throw new IllegalArgumentException("Parent is trashed");
        if (parent.getType() != NodeType.FOLDER) throw new IllegalArgumentException("Parent must be a folder");

        return parent;
    }

    private void ensureNoNameConflict(UUID ownerId, UUID parentId, String name) {
        boolean exists = (parentId == null)
                ? storageNodeRepository.existsByOwnerIdAndParentIsNullAndNameIgnoreCaseAndTrashedFalse(ownerId, name)
                : storageNodeRepository.existsByOwnerIdAndParent_IdAndNameIgnoreCaseAndTrashedFalse(ownerId, parentId, name);

        if (exists) throw new IllegalArgumentException("A node with that name already exists in the target folder");
    }

    private @NonNull Optional<StorageNode> findSiblingByName(UUID ownerId, UUID parentId, String name) {
        List<StorageNode> siblings = listChildren(ownerId, parentId);
        return siblings.stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();
    }

    private @NonNull String buildPath(StorageNode parent, String name) {
        if (parent == null) return "/" + sanitize(name);

        String p = parent.getPath();

        if (p == null || p.isBlank()) p = "/";
        if ("/".equals(p)) return "/" + sanitize(name);

        return p + "/" + sanitize(name);
    }

    private @NonNull String sanitize(@NonNull String segment) {
        return segment.trim().replace("/", "-");
    }

    private void markSubtreeTrashed(@NonNull StorageNode root, OffsetDateTime now) {
        root.setTrashed(true);
        root.setTrashedAt(now);
        root.setUpdatedAt(now);
        storageNodeRepository.save(root);

        for (StorageNode child : childrenOf(root))
            if (!child.isTrashed()) markSubtreeTrashed(child, now);
    }

    private void untrashSubtree(@NonNull StorageNode root) {
        OffsetDateTime now = OffsetDateTime.now();
        root.setTrashed(false);
        root.setTrashedAt(null);
        root.setUpdatedAt(now);
        storageNodeRepository.save(root);

        for (StorageNode child : childrenOf(root))
            if (child.isTrashed()) untrashSubtree(child);
    }

    private @NonNull List<StorageNode> childrenOf(@NonNull StorageNode parent) {
        UUID ownerId = parent.getOwnerId();
        UUID parentId = parent.getId();

        List<StorageNode> notTrashed = storageNodeRepository.findByOwnerIdAndParent_IdAndTrashedFalseOrderByTypeAscNameAsc(ownerId, parentId);
        List<StorageNode> trashed = storageNodeRepository.findByOwnerIdAndParent_IdAndTrashedTrueOrderByTypeAscNameAsc(ownerId, parentId);

        List<StorageNode> all = new ArrayList<>(notTrashed.size() + trashed.size());

        all.addAll(notTrashed);
        all.addAll(trashed);

        return all;
    }

    private void updatePathsRecursively(StorageNode root) {
        for (StorageNode child : childrenOf(root)) {
            child.setPath(buildPath(root, child.getName()));
            child.setUpdatedAt(OffsetDateTime.now());

            storageNodeRepository.save(child);
            updatePathsRecursively(child);
        }
    }

    private @NonNull List<StorageNode> collectSubtree(StorageNode root) {
        List<StorageNode> result = new ArrayList<>();
        Deque<StorageNode> stack = new ArrayDeque<>();

        stack.push(root);

        while (!stack.isEmpty()) {
            StorageNode current = stack.pop();
            result.add(current);

            for (StorageNode child : childrenOf(current)) stack.push(child);
        }

        return result;
    }

    private int depth(@NonNull StorageNode node) {
        int d = 0;
        StorageNode tempNode = node.getParent();

        while (tempNode != null) {
            d++;
            tempNode = tempNode.getParent();
        }

        return d;
    }

    private String json(String k, String v) {
        if (k == null) return null;
        return "{\"" + escape(k) + "\":\"" + escape(v) + "\"}";
    }

    private @NonNull String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}