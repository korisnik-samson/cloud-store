package com.samson.cloudstore.controllers;

import com.samson.cloudstore.config.StorageProperties;
import com.samson.cloudstore.dto.StorageNodeDto;
import com.samson.cloudstore.dto.StorageNodeMapper;
import com.samson.cloudstore.dto.UploadDtos;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.services.ObjectStorageService;
import com.samson.cloudstore.services.StorageNodeService;
import com.samson.cloudstore.services.UserService;
import io.minio.StatObjectResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/uploads")
public class StorageUploadController {

    private final StorageNodeService storageNodeService;
    private final ObjectStorageService objectStorageService;
    private final UserService userService;
    private final StorageProperties storageProperties;

    @Autowired
    public StorageUploadController(StorageNodeService storageNodeService, ObjectStorageService objectStorageService, UserService userService,
                                   StorageProperties storageProperties) {
        this.storageNodeService = storageNodeService;
        this.objectStorageService = objectStorageService;
        this.userService = userService;
        this.storageProperties = storageProperties;
    }

    @PostMapping("/initiate")
    public ResponseEntity<UploadDtos.InitiateUploadResponse> initiate(@Valid @RequestBody UploadDtos.InitiateUploadRequest request) {
        Users user = userService.getAuthenticatedUser();

        // server-side size check
        long max = storageProperties.getMaxFileSizeBytes() == null ? Long.MAX_VALUE : storageProperties.getMaxFileSizeBytes();
        if (request.sizeBytes() != null && request.sizeBytes() > max) {
            throw new IllegalArgumentException("File exceeds max size");
        }

        String objectKey = "users/" + user.getUserId().toString() + "/" + UUID.randomUUID() + "/" + request.fileName();
        String uploadUrl = objectStorageService.generatePresignedUploadUrl(objectKey, 15);

        return ResponseEntity.ok(new UploadDtos.InitiateUploadResponse(objectKey, uploadUrl, 900));
    }

    @PostMapping("/complete")
    public ResponseEntity<StorageNodeDto> complete(@Valid @RequestBody UploadDtos.CompleteUploadRequest request) {
        Users user = userService.getAuthenticatedUser();

        StatObjectResponse stat = objectStorageService.stat(request.objectKey());

        if (request.sizeBytes() != null && stat.size() != request.sizeBytes()) {
            throw new IllegalArgumentException("Size mismatch");
        }

        StorageNode node = storageNodeService.saveUploadedFileMetadata(
                user,
                request.parentId(),
                request.fileName(),
                request.mimeType(),
                stat.size(),
                request.objectKey(),
                request.checksumSha256()
        );

        return ResponseEntity.ok(StorageNodeMapper.toDto(node));
    }
}