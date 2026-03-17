package com.samson.cloudstore.dto;

import com.samson.cloudstore.models.StorageNode;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

public class StorageNodeMapper {
    private StorageNodeMapper() {}

    @Contract("_ -> new")
    public static @NonNull StorageNodeDto toDto(@NonNull StorageNode node) {
        return new StorageNodeDto(
                node.getId(),
                node.getOwnerId(),
                node.getParent() == null ? null : node.getParent().getId(),
                node.getName(),
                node.getPath(),
                node.getType(),
                node.getMimeType(),
                node.getSizeBytes(),
                node.isTrashed(),
                node.getTrashedAt(),
                node.getCreatedAt(),
                node.getUpdatedAt()
        );
    }
}