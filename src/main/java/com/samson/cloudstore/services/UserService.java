package com.samson.cloudstore.services;

import com.samson.cloudstore.dto.CreateUserRequest;
import com.samson.cloudstore.dto.UserResponse;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "users", key = "#id")
    public UserResponse get(UUID id) {
        Users u = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        return new UserResponse(u.getUserId().toString(), u.getUsername(), u.getEmail(), u.getUserRole().name());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "allUsers")
    public Iterable<UserResponse> getAllUsers() {
        return repository.findAll().stream().map(
                users -> new UserResponse(
                        users.getUserId().toString(), users.getUsername(),
                        users.getEmail(), users.getUserRole().name()
                )
        ).toList();
    }

    @Transactional
    @CacheEvict(value = "allUsers", allEntries = true)
    public UserResponse create(@NotNull CreateUserRequest request) {
        // perform any conflict checks
        if (repository.existsByEmail(request.email())) throw new ResponseStatusException(HttpStatus.CONFLICT, "email already exists");

        if (repository.existsByUsername(request.username())) throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exists");

        if (request.password().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password cannot be blank");

        if (request.userRole() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user role cannot be null");

        // create new user object
        Users newUser = Users.builder()
                .username(request.username())
                .email(request.email())
                .userRole(request.userRole())
                .password(encoder.encode(request.password()))
                .build();

        newUser = repository.save(newUser);

        return new UserResponse(
                newUser.getUserId().toString(), newUser.getUsername(),
                newUser.getEmail(), newUser.getUserRole().name()
        );
    }

    public UUID getAuthenticatedUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null || auth.getName().isBlank())
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized"
            );

        try {
            return UUID.fromString(auth.getName());

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid auth subject"
            );
        }
    }

    @Transactional(readOnly = true)
    public Users getAuthenticatedUser() {
        UUID userId = getAuthenticatedUserId();

        if (userId == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");

        return repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user"));
    }
}
