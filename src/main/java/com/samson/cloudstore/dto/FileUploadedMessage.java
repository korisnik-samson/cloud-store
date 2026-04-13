package com.samson.cloudstore.dto;

import java.util.UUID;

public record FileUploadedMessage(
        UUID nodeId,
        UUID ownerId,
        String fileName,
        String mimeType,
        String objectKey,
        long sizeBytes
) {}
