package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.ShareDtos;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.services.CurrentUserService;
import com.samson.cloudstore.services.ShareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shares")
@RequiredArgsConstructor
public class ShareController {

    private final CurrentUserService currentUserService;
    private final ShareService shareService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShareDtos.CreateShareResponse create(@Valid @RequestBody ShareDtos.CreateShareRequest req) {
        Users user = currentUserService.requireCurrentUser();
        return shareService.createShare(user, req);
    }

    // Public endpoint (permitAll in SecurityConfig): used by Next.js /share/[token] page
    @PostMapping("/resolve/{token}")
    public ShareDtos.ResolveShareResponse resolve(@PathVariable String token, @RequestBody(required = false) ShareDtos.ResolveShareRequest req) throws Exception {
        return shareService.resolve(token, req);
    }
}
