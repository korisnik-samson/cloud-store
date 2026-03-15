package com.samson.cloudstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UploadDtos {
    public record InitiateUploadRequest(
            @NotBlank String fileName,
            @NotBlank String mimeType,
            @NotNull Long sizeBytes,
            UUID parentId
    ) {}

    public record InitiateUploadResponse(
            String objectKey,
            String uploadUrl,
            long expiresInSeconds
    ) {}

    public record CompleteUploadRequest(
            @NotBlank String objectKey,
            @NotBlank String fileName,
            @NotBlank String mimeType,
            @NotNull Long sizeBytes,
            UUID parentId,
            String checksumSha256
    ) {}
}