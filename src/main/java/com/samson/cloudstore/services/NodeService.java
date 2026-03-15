package com.samson.cloudstore.services;

import com.samson.cloudstore.dto.NodeDtos;
import com.samson.cloudstore.models.FileMetaData;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.FileMetaDataRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final FileMetaDataRepository fileMetaDataRepository;

    @Transactional(readOnly = true)
    public List<NodeDtos.NodeResponse> list(Users user, UUID parentId) {
        List<FileMetaData> nodes = (parentId == null) ? fileMetaDataRepository.listRoot(user) : fileMetaDataRepository.listByParent(user, parentId);

        return nodes.stream().map(NodeService::toDto).toList();
    }

    @Transactional
    public NodeDtos.NodeResponse createFolder(Users user, UUID parentId, String name) {
        name = sanitizeName(name);

        if (fileMetaDataRepository.nameExistsInFolder(user, parentId, name))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An item with that name already exists in this folder");

        FileMetaData parent = null;
        if (parentId != null) {
            parent = fileMetaDataRepository.findOwnedActive(parentId, user)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent folder not found"));

            if (!parent.isFolder()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent must be a folder");
        }

        FileMetaData folder = FileMetaData.builder()
                .fileName(name)
                .contentType("application/x-directory")
                .isFolder(true)
                .owner(user)
                .parentFolder(parent)
                .isTrashed(false)
                .build();

        FileMetaData saved = fileMetaDataRepository.save(folder);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public FileMetaData requireOwnedFile(Users user, UUID nodeId) {
        FileMetaData node = fileMetaDataRepository.findOwnedActive(nodeId, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found"));

        if (node.isFolder())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Folders cannot be downloaded");

        if (node.getS3ObjectName() == null || node.getS3ObjectName().isBlank())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "File has no object key");

        return node;
    }

    @Transactional
    public void trash(Users user, UUID nodeId) {
        FileMetaData node = fileMetaDataRepository.findOwnedAny(nodeId, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found"));

        if (node.isTrashed()) return;

        node.setTrashed(true);
        node.setTrashed_at(OffsetDateTime.now());

        fileMetaDataRepository.save(node);
    }

    @Transactional(readOnly = true)
    public List<NodeDtos.NodeResponse> search(Users user, String q) {
        if (q == null || q.isBlank()) return List.of();
        return fileMetaDataRepository.searchByName(user, q.trim()).stream().map(NodeService::toDto).toList();
    }

    @Transactional(readOnly = true)
    public FileMetaData requireParentFolder(Users user, UUID parentId) {
        if (parentId == null) return null;

        FileMetaData parent = fileMetaDataRepository.findOwnedActive(parentId, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent folder not found"));

        if (!parent.isFolder()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent must be a folder");

        return parent;
    }

    @Contract("_ -> new")
    public static NodeDtos.@NonNull NodeResponse toDto(@NonNull FileMetaData n) {
        return new NodeDtos.NodeResponse(
                n.getId(),
                n.getFileName(),
                n.isFolder() ? "FOLDER" : "FILE",
                n.getContentType(),
                n.getSize(),
                n.getParentFolder() == null ? null : n.getParentFolder().getId(),
                n.getUpdated_at()
        );
    }

    @Contract("null -> fail")
    private @NonNull String sanitizeName(String name) {
        if (name == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");

        String s = name.trim();

        if (s.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");

        if (s.contains("/") || s.contains("\\") || s.equals(".") || s.equals("..")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid name");

        return s;
    }
}
