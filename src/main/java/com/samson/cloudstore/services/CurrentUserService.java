package com.samson.cloudstore.services;

import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.UserRepository;
import com.samson.cloudstore.utilities.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    @Transactional
    public Users requireCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null || auth.getName().isBlank())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");

        String subject = auth.getName();
        UUID userId;

        try {
            userId = UUID.fromString(subject);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid auth subject: " + subject);
        }

        var userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }

        // Auto-provisioning for OAuth2 users if they don't exist locally
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            
            String email = jwt.getClaimAsString("email");
            String username = jwt.getClaimAsString("preferred_username");
            if (username == null) username = jwt.getClaimAsString("username");
            if (username == null) username = email;

            if (email == null) {
                log.warn("OAuth2 token for subject {} missing email claim", subject);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email claim missing in token");
            }

            log.info("Auto-provisioning user {} from OAuth2 token", email);
            
            Users newUser = Users.builder()
                    .userId(userId)
                    .email(email)
                    .username(username != null ? username : email)
                    .password("OAUTH2_EXTERNAL") // Placeholder
                    .userRole(UserRole.USER)
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .version(0)
                    .build();

            return userRepository.save(newUser);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user: " + userId);
    }
}
