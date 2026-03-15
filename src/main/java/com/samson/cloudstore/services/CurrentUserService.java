package com.samson.cloudstore.services;

import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public Users requireCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null || auth.getName().isBlank())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");

        UUID userId;

        try {
            userId = UUID.fromString(auth.getName());

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid auth subject");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user"));
    }
}
