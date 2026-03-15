package com.samson.cloudstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.UUID;

public class NodeDtos {

    public record NodeResponse(
            UUID id,
            String name,
            String type,      // FILE or FOLDER
            String mimeType,
            Long sizeBytes,
            UUID parentId,
            OffsetDateTime updatedAt
    ) {}

    public record CreateFolderRequest(
            @NotBlank @Size(min = 1, max = 255) String name
    ) {}

    public record DownloadUrlResponse(String url) {}
}
