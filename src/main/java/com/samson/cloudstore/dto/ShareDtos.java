package com.samson.cloudstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ShareDtos {

    public record CreateShareRequest(
            @NotNull UUID nodeId,
            LocalDateTime createdAt,
            LocalDateTime expiredAt,
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
