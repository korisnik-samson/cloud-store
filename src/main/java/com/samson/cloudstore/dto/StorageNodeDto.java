package com.samson.cloudstore.dto;

import com.samson.cloudstore.utilities.NodeType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StorageNodeDto(
        UUID id,
        UUID ownerId,
        UUID parentId,
        String name,
        String path,
        NodeType type,
        String mimeType,
        Long sizeBytes,
        boolean trashed,
        OffsetDateTime trashedAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}