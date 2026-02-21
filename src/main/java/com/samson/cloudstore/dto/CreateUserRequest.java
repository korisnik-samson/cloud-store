package com.samson.cloudstore.dto;
import com.samson.cloudstore.util.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest (
        @NotBlank @Size(max = 50) String username,
        @NotBlank @Size(max = 254) String email,
        @NotBlank @Size(min = 8, max = 128) String password,
        @NotBlank UserRole userRole
) {}

