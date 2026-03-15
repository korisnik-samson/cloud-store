package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.NodeDtos;
import com.samson.cloudstore.dto.UploadDtos;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.services.CurrentUserService;
import com.samson.cloudstore.services.UploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final CurrentUserService currentUserService;
    private final UploadService uploadService;

    @PostMapping("/initiate")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadDtos.InitiateUploadResponse initiate(@Valid @RequestBody UploadDtos.InitiateUploadRequest req) throws Exception {
        Users user = currentUserService.requireCurrentUser();
        return uploadService.initiate(user, req);
    }

    @PostMapping("/complete")
    @ResponseStatus(HttpStatus.CREATED)
    public NodeDtos.NodeResponse complete(@Valid @RequestBody UploadDtos.CompleteUploadRequest req) throws Exception {
        Users user = currentUserService.requireCurrentUser();
        return uploadService.complete(user, req);
    }
}
