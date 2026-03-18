package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.StorageUsageResponse;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.services.CurrentUserService;
import com.samson.cloudstore.services.StorageUsageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UsageController {

    private final CurrentUserService currentUserService;
    private final StorageUsageService storageUsageService;

    public UsageController(CurrentUserService currentUserService,
                           StorageUsageService storageUsageService) {
        this.currentUserService = currentUserService;
        this.storageUsageService = storageUsageService;
    }

    @GetMapping("/usage")
    public StorageUsageResponse getUsage() {
        Users user = currentUserService.requireCurrentUser();
        return storageUsageService.getUsageForUser(user);
    }
}