package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.ShareDtos;
import com.samson.cloudstore.models.ShareLink;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.services.CurrentUserService;
import com.samson.cloudstore.services.ShareService;
import com.samson.cloudstore.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shares")
@RequiredArgsConstructor
public class ShareController {

    private final CurrentUserService currentUserService;
    private final UserService userService;
    private final ShareService shareService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShareDtos.CreateShareResponse create(@Valid @RequestBody ShareDtos.CreateShareRequest req) {
        /*Users user = currentUserService.requireCurrentUser();
        return shareService.createShare(user, req);*/

        UUID userId = userService.getAuthenticatedUserId();
        LocalDateTime expiresAt = null;

        if (req.createdAt() != null)
            expiresAt = req.expiredAt();
        else if (req.expiresHours() != null && req.expiresHours() > 0)
            expiresAt = LocalDateTime.now().plusHours(req.expiresHours());

        ShareLink link = shareService.createShare(
                userId,
                req.nodeId(),
                expiresAt,
                req.maxDownloads(),
                req.password()
        );
    }

    // Public endpoint (permitAll in SecurityConfig): used by Next.js /share/[token] page
    @PostMapping("/resolve/{token}")
    public ShareDtos.ResolveShareResponse resolve(@PathVariable String token, @RequestBody(required = false) ShareDtos.ResolveShareRequest req) throws Exception {
        return shareService.resolve(token, req);
    }
}
