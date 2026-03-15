package com.samson.cloudstore.dto;
import com.samson.cloudstore.utilities.UserRole;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

public record CreateUserRequest (
        @NotNull @Size(max = 50) String username,
        @NotNull @Size(max = 254) String email,
        @NotNull @Size(min = 8, max = 128) String password,
        @NotNull UserRole userRole
) {}

