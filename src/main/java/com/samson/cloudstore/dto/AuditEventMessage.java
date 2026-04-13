package com.samson.cloudstore.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AuditEventMessage(
        UUID userId,
        String action,
        UUID nodeId,
        String metadata,
        OffsetDateTime createdAt
) {}
