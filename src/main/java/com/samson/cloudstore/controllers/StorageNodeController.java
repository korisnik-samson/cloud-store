package com.samson.cloudstore.controllers;

import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.services.CurrentUserService;
import com.samson.cloudstore.services.ObjectStorageService;
import com.samson.cloudstore.services.StorageNodeService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/nodes")
@RequiredArgsConstructor
public class StorageNodeController {

    private final StorageNodeService storageNodeService;
    private final CurrentUserService currentUserService;
    private final ObjectStorageService objectStorageService;

    @GetMapping
    public List<Map<String, Object>> list(@RequestParam(required = false) UUID parentId) {
        Users user = currentUserService.requireCurrentUser();
        return storageNodeService.list(user, parentId).stream().map(this::toDto).toList();
    }

    @PostMapping("/folders")
    public Map<String, Object> createFolder(@RequestBody @NonNull Map<String, String> body, @RequestParam(required = false) UUID parentId) {
        Users user = currentUserService.requireCurrentUser();
        StorageNode folder = storageNodeService.createFolder(user, parentId, body.get("name"));

        return toDto(folder);
    }

    @GetMapping("/{nodeId}/download-url")
    public Map<String, String> getDownloadUrl(@PathVariable UUID nodeId) throws Exception {
        Users user = currentUserService.requireCurrentUser();
        StorageNode node = storageNodeService.getOwnedNode(user, nodeId);

        if (!"FILE".equals(node.getType().name()))
            throw new IllegalArgumentException("Only files can be downloaded");

        String url = objectStorageService.generatePresignedDownloadUrl(node.getObjectKey(), 10);

        return Map.of("url", url);
    }

    @DeleteMapping("/{nodeId}")
    public Map<String, String> trash(@PathVariable UUID nodeId) {
        Users user = currentUserService.requireCurrentUser();
        storageNodeService.trashNode(user, nodeId);

        return Map.of("status", "trashed");
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam @NotBlank String q) {
        Users user = currentUserService.requireCurrentUser();

        return storageNodeService.searchByName(user, q).stream().map(this::toDto).toList();
    }

    @Contract("_ -> new")
    private @NonNull @Unmodifiable Map<String, Object> toDto(@NonNull StorageNode node) {
        Map<String, Object> dto = new LinkedHashMap<>();

        dto.put("id", node.getId());
        dto.put("name", node.getName());
        dto.put("type", node.getType().name());
        dto.put("mimeType", node.getMimeType() == null ? "" : node.getMimeType());
        dto.put("sizeBytes", node.getSizeBytes() == null ? 0 : node.getSizeBytes());
        dto.put("parentId", node.getParent() == null ? null : node.getParent().getId());
        dto.put("updatedAt", node.getUpdatedAt());

        return dto;
    }
}