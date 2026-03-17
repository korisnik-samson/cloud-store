package com.samson.cloudstore.controllers;

import com.samson.cloudstore.models.ShareLink;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.services.ShareService;
import com.samson.cloudstore.services.UserService;
import com.samson.cloudstore.utilities.NodeType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;
    private final UserService userService;

    // -----------------
    // Authenticated API
    // -----------------

    @PostMapping("/api/v1/shares")
    public ResponseEntity<Map<String, Object>> create(@RequestBody CreateShareRequest req) {
        UUID userId = userService.getAuthenticatedUserId();

        OffsetDateTime expiresAt = null;
        if (req.getExpiresAt() != null) expiresAt = req.getExpiresAt();
        else if (req.getExpiresHours() != null && req.getExpiresHours() > 0) expiresAt = OffsetDateTime.now().plusHours(req.getExpiresHours());

        ShareLink link = shareService.createShare(
                userId,
                req.getNodeId(),
                expiresAt,
                req.getMaxDownloads(),
                req.getPassword()
        );

        return ResponseEntity.ok(Map.of(
                "id", link.getId(),
                "token", link.getToken(),
                "expiresAt", link.getExpiresAt(),
                "maxDownloads", link.getMaxDownloads(),
                "active", link.isActive()
        ));
    }

    @GetMapping("/api/v1/shares")
    public ResponseEntity<List<Map<String, Object>>> listMyShares() {
        UUID userId = userService.getAuthenticatedUserId();
        List<ShareLink> shares = shareService.listMyShares(userId);

        List<Map<String, Object>> response = shares.stream().map(s -> {
            StorageNode n = s.getNode();
            return Map.<String, Object>of(
                    "id", s.getId(),
                    "token", s.getToken(),
                    "active", s.isActive(),
                    "expiresAt", s.getExpiresAt(),
                    "maxDownloads", s.getMaxDownloads(),
                    "downloadCount", s.getDownloadCount(),
                    "node", Map.of(
                            "id", n.getId(),
                            "name", n.getName(),
                            "type", n.getType()
                    )
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/v1/shares/{id}")
    public ResponseEntity<?> revoke(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        shareService.revoke(userId, id);

        return ResponseEntity.noContent().build();
    }

    // -----------------
    // Public API (used by Next.js share page)
    // -----------------

    /**
     * Backwards-compatible endpoint used by the UI: POST /api/v1/shares/resolve/{token}
     * If the share is password-protected and no/invalid password is supplied, returns 401.
     */
    @PostMapping("/api/v1/shares/resolve/{token}")
    public ResponseEntity<?> resolveForUi(@PathVariable String token, @RequestBody(required = false) ResolveRequest req) {
        ShareLink link = shareService.requireValid(token);
        StorageNode node = link.getNode();

        if (link.getPasswordHash() != null) {
            String pwd = req == null ? null : req.getPassword();
            try {
                // this will throw if invalid
                shareService.verifyPassword(link, pwd);

            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Password required or invalid"));
            }
        }

        String downloadUrl = shareService.getDownloadUrl(token, req == null ? null : req.getPassword());

        return ResponseEntity.ok(Map.of(
                "name", node.getName(),
                "type", node.getType(),
                "sizeBytes", node.getSizeBytes(),
                "downloadUrl", downloadUrl
        ));
    }

    @GetMapping("/api/v1/shares/public/{token}")
    public ResponseEntity<Map<String, Object>> resolve(@PathVariable String token) {
        ShareLink link = shareService.requireValid(token);
        StorageNode n = link.getNode();

        return ResponseEntity.ok(Map.of(
                "token", link.getToken(),
                "expiresAt", link.getExpiresAt(),
                "maxDownloads", link.getMaxDownloads(),
                "downloadCount", link.getDownloadCount(),
                "passwordRequired", link.getPasswordHash() != null,
                "node", Map.of(
                        "id", n.getId(),
                        "name", n.getName(),
                        "type", n.getType(),
                        "mimeType", n.getMimeType(),
                        "sizeBytes", n.getSizeBytes()
                )
        ));
    }

    @PostMapping("/api/v1/shares/public/{token}/download-url")
    public ResponseEntity<Map<String, Object>> downloadUrl(@PathVariable String token,
                                                           @RequestBody(required = false) ResolveRequest req) {
        String password = req == null ? null : req.getPassword();
        String url = shareService.getDownloadUrl(token, password);
        return ResponseEntity.ok(Map.of("url", url));
    }

    @Data
    public static class CreateShareRequest {
        private UUID nodeId;
        // Either provide expiresAt or expiresHours.
        private OffsetDateTime expiresAt;
        private Integer expiresHours;
        private Integer maxDownloads;
        private String password;
    }

    @Data
    public static class ResolveRequest {
        private String password;
    }
}