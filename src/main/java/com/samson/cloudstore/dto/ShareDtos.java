package com.samson.cloudstore.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ShareDtos {

    public record CreateShareRequest(
            @NotNull UUID nodeId,
            Integer expiresHours,
            String password,
            Integer maxDownloads
    ) {}

    public record CreateShareResponse(
            String token,
            String url
    ) {}

    public record ResolveShareRequest(
            String password
    ) {}

    public record ResolveShareResponse(
            String name,
            String type,
            Long sizeBytes,
            String downloadUrl
    ) {}
}
