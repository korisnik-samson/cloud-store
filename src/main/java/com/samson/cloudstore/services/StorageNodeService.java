package com.samson.cloudstore.services;

import com.samson.cloudstore.utilities.NodeType;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.StorageNodeRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageNodeService {

    @Autowired
    private final StorageNodeRepository nodeRepository;

    @Transactional(readOnly = true)
    public List<StorageNode> list(Users owner, UUID parentId) {
        if (parentId == null) return nodeRepository.findByOwnerAndParentIsNullAndTrashedFalseOrderByTypeAscNameAsc(owner);
        return nodeRepository.findByOwnerAndParentIdAndTrashedFalseOrderByTypeAscNameAsc(owner, parentId);
    }

    @Transactional
    public StorageNode createFolder(Users owner, UUID parentId, String name) {
        name = sanitizeName(name);

        StorageNode parent = null;
        String path = "/";
        if (parentId != null) {
            parent = nodeRepository.findByIdAndOwner(parentId, owner).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent folder not found")
            );

            if (parent.getType() != NodeType.FOLDER)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent must be a folder");

            if (nodeRepository.existsByOwnerAndParentIdAndNameIgnoreCaseAndTrashedFalse(owner, parentId, name))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Folder already exists");

            path = parent.getPath().equals("/") ? "/" + parent.getName() : parent.getPath() + "/" + parent.getName();

        } else {
            if (nodeRepository.existsByOwnerAndParentIsNullAndNameIgnoreCaseAndTrashedFalse(owner, name))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Folder already exists");
        }

        StorageNode folder = StorageNode.builder().owner(owner).parent(parent)
                .name(name).type(NodeType.FOLDER).path(path)
                .trashed(false).build();

        return nodeRepository.save(folder);
    }

    @Transactional
    public StorageNode saveUploadedFileMetadata(
            Users owner,
            UUID parentId,
            String fileName,
            String mimeType,
            long sizeBytes,
            String objectKey,
            String checksumSha256
    ) {
        fileName = sanitizeName(fileName);

        StorageNode parent = null;
        String path = "/";

        if (parentId != null) {
            parent = nodeRepository.findByIdAndOwner(parentId, owner)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent folder not found"));

            if (parent.getType() != NodeType.FOLDER)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent must be folder");

        }

        StorageNode node = StorageNode.builder().owner(owner).parent(parent).name(fileName)
                .type(NodeType.FILE).mimeType(mimeType).sizeBytes(sizeBytes)
                .objectKey(objectKey).checksumSha256(checksumSha256)
                .path(pathOf(parent)).trashed(false).build();

        return nodeRepository.save(node);
    }

    @Transactional
    public void trashNode(Users owner, UUID nodeId) {
        StorageNode node = nodeRepository.findByIdAndOwner(nodeId, owner).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found"));

        node.setTrashed(true);
        node.setTrashedAt(OffsetDateTime.now());

        nodeRepository.save(node);
    }

    @Transactional(readOnly = true)
    public StorageNode getOwnedNode(Users owner, UUID nodeId) {
        return nodeRepository.findByIdAndOwner(nodeId, owner).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found"));
    }

    @Transactional(readOnly = true)
    public List<StorageNode> searchByName(Users owner, String q) {
        return nodeRepository.findByOwnerAndNameContainingIgnoreCaseAndTrashedFalseOrderByUpdatedAtDesc(owner, q);
    }

    private @NonNull String pathOf(StorageNode parent) {
        if (parent == null) return "/";

        return parent.getPath().equals("/") ? "/" + parent.getName() : parent.getPath() + "/" + parent.getName();
    }

    @Contract("null -> fail")
    private @NonNull String sanitizeName(String name) {
        if (name == null || name.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");

        String trimmed = name.trim(); //.toLowerCase().replaceAll("[^a-zA-Z0-9._-]", "_");

        if (trimmed.contains("/") || trimmed.contains("\\") || trimmed.equals(".") || trimmed.equals(".."))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file/folder name");

        return trimmed;
    }
}