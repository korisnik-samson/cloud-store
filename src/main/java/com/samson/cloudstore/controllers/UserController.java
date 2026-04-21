package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.CreateUserRequest;
import com.samson.cloudstore.dto.UserResponse;
import com.samson.cloudstore.services.CurrentUserService;
import com.samson.cloudstore.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;
    private final CurrentUserService currentUserService;

    public UserController(UserService service, CurrentUserService currentUserService) {
        this.service = service;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser() {
        var user = currentUserService.requireCurrentUser();
        return new UserResponse(
                user.getUserId().toString(),
                user.getUsername(),
                user.getEmail(),
                user.getUserRole().name()
        );
    }

    @GetMapping("/{userId}")
    public UserResponse get(@PathVariable UUID userId) {
        return service.get(userId);
    }

    @GetMapping
    public Iterable<UserResponse> getAll() {
        return service.getAllUsers();
    }

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest requestBody) {
        return service.create(requestBody);
    }
}