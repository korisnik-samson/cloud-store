package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.StorageNodeDto;
import com.samson.cloudstore.dto.StorageNodeMapper;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.services.StorageNodeService;
import com.samson.cloudstore.services.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/nodes")
public class StorageNodeController {

    private final StorageNodeService storageNodeService;
    private final UserService userService;

    public StorageNodeController(StorageNodeService storageNodeService, UserService userService) {
        this.storageNodeService = storageNodeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<StorageNodeDto>> list(@RequestParam(required = false) UUID parentId) {
        UUID userId = userService.getAuthenticatedUserId();
        List<StorageNodeDto> dtos = storageNodeService.listChildren(userId, parentId).stream().map(StorageNodeMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/trash")
    public ResponseEntity<List<StorageNodeDto>> listTrash() {
        UUID userId = userService.getAuthenticatedUserId();
        List<StorageNodeDto> dtos = storageNodeService.listTrashRoots(userId).stream().map(StorageNodeMapper::toDto).toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/folders")
    public ResponseEntity<StorageNodeDto> createFolder(@RequestParam(required = false) UUID parentId,
                                                       @RequestBody CreateFolderRequest request) {
        UUID userId = userService.getAuthenticatedUserId();
        UUID effectiveParentId = request.getParentId() != null ? request.getParentId() : parentId;
        StorageNode node = storageNodeService.createFolder(userId, effectiveParentId, request.getName());

        return ResponseEntity.ok(StorageNodeMapper.toDto(node));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable UUID id) {
        // Backwards-compatible: treat DELETE as move-to-trash
        UUID userId = userService.getAuthenticatedUserId();
        storageNodeService.trash(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/trash")
    public ResponseEntity<?> trash(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        storageNodeService.trash(userId, id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<?> restore(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        storageNodeService.restore(userId, id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/purge")
    public ResponseEntity<?> purge(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        storageNodeService.purge(userId, id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/rename")
    public ResponseEntity<StorageNodeDto> rename(@PathVariable UUID id, @RequestBody RenameRequest request) {
        UUID userId = userService.getAuthenticatedUserId();
        return ResponseEntity.ok(StorageNodeMapper.toDto(storageNodeService.rename(userId, id, request.getName())));
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<StorageNodeDto> move(@PathVariable UUID id, @RequestBody MoveRequest request) {
        UUID userId = userService.getAuthenticatedUserId();
        return ResponseEntity.ok(StorageNodeMapper.toDto(storageNodeService.move(userId, id, request.getParentId())));
    }

    @GetMapping("/{id}/download-url")
    public ResponseEntity<Map<String, String>> getDownloadUrl(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        String url = storageNodeService.getDownloadUrl(userId, id);

        return ResponseEntity.ok(Map.of("url", url));
    }

    @GetMapping("/{id}/versions")
    public ResponseEntity<?> versions(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();

        return ResponseEntity.ok(storageNodeService.listVersions(userId, id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<StorageNodeDto>> search(@RequestParam String q) {
        UUID userId = userService.getAuthenticatedUserId();
        List<StorageNodeDto> dtos = storageNodeService.search(userId, q).stream().map(StorageNodeMapper::toDto).toList();

        return ResponseEntity.ok(dtos);
    }

    @Data
    public static class CreateFolderRequest {
        private UUID parentId;
        private String name;
    }

    @Data
    public static class RenameRequest {
        private String name;
    }

    @Data
    public static class MoveRequest {
        private UUID parentId;
    }
}