package com.samson.cloudstore.dto;

public record UserResponse (
        String id,
        String username,
        String email,
        String userRole
) {}
