package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.NodeDtos;
import com.samson.cloudstore.models.FileMetaData;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.services.CurrentUserService;
import com.samson.cloudstore.services.NodeService;
import com.samson.cloudstore.services.ObjectStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nodes")
@RequiredArgsConstructor
public class NodeController {

    private final CurrentUserService currentUserService;
    private final NodeService nodeService;
    private final ObjectStorageService objectStorageService;

    @GetMapping
    public List<NodeDtos.NodeResponse> list(@RequestParam(required = false) UUID parentId) {
        Users user = currentUserService.requireCurrentUser();
        return nodeService.list(user, parentId);
    }

    @PostMapping("/folders")
    public NodeDtos.NodeResponse createFolder(
            @RequestParam(required = false) UUID parentId,
            @Valid @RequestBody NodeDtos.@NonNull CreateFolderRequest req
    ) {
        Users user = currentUserService.requireCurrentUser();
        return nodeService.createFolder(user, parentId, req.name());
    }

    @DeleteMapping("/{nodeId}")
    public void trash(@PathVariable UUID nodeId) {
        Users user = currentUserService.requireCurrentUser();
        nodeService.trash(user, nodeId);
    }

    @GetMapping("/{nodeId}/download-url")
    public NodeDtos.DownloadUrlResponse downloadUrl(@PathVariable UUID nodeId) throws Exception {
        Users user = currentUserService.requireCurrentUser();
        FileMetaData file = nodeService.requireOwnedFile(user, nodeId);
        String url = objectStorageService.generatePresignedDownloadUrl(file.getS3ObjectName(), 10);

        return new NodeDtos.DownloadUrlResponse(url);
    }

    @GetMapping("/search")
    public List<NodeDtos.NodeResponse> search(@RequestParam String q) {
        Users user = currentUserService.requireCurrentUser();
        return nodeService.search(user, q);
    }
}
