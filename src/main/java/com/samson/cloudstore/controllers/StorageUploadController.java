package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.UploadDtos;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.services.CurrentUserService;
import com.samson.cloudstore.services.ObjectStorageService;
import com.samson.cloudstore.services.StorageNodeService;
import io.minio.StatObjectResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/uploads")
@RequiredArgsConstructor
public class StorageUploadController {

    private final ObjectStorageService objectStorageService;
    private final StorageNodeService storageNodeService;
    private final CurrentUserService currentUserService;

    @PostMapping("/initiate")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadDtos.InitiateUploadResponse initiate(@Valid @RequestBody UploadDtos.@NonNull InitiateUploadRequest req) throws Exception {
        Users user = currentUserService.requireCurrentUser();

        String objectKey = "users/" + user.getUserId() + "/" + UUID.randomUUID() + "/" + req.fileName();
        String uploadUrl = objectStorageService.generatePresignedUploadUrl(objectKey, 15);

        return new UploadDtos.InitiateUploadResponse(objectKey, uploadUrl, 900);
    }

    @PostMapping("/complete")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> complete(@Valid @RequestBody UploadDtos.@NonNull CompleteUploadRequest req) throws Exception {
        Users user = currentUserService.requireCurrentUser();

        StatObjectResponse stat = objectStorageService.stat(req.objectKey());

        if (stat.size() != req.sizeBytes())
            throw new IllegalStateException("Uploaded size mismatch");

        StorageNode node = storageNodeService.saveUploadedFileMetadata(
                user,
                req.parentId(),
                req.fileName(),
                req.mimeType(),
                req.sizeBytes(),
                req.objectKey(),
                req.checksumSha256()
        );

        return Map.of(
                "id", node.getId(),
                "name", node.getName(),
                "type", node.getType(),
                "sizeBytes", node.getSizeBytes()
        );
    }
}