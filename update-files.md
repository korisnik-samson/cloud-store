# Cloud Store Backend - Updated Files (Dump)

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.0.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.samson</groupId>
    <artifactId>cloud-store</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>cloud-store</name>
    <description>cloud-store</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>25</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webmvc</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <version>12.1.0</version>
        </dependency>

        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-database-postgresql</artifactId>
            <version>12.1.0</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webmvc-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- Source: https://mvnrepository.com/artifact/software.amazon.awssdk/s3 -->
        <dependency>
            <groupId>software.amazon.awssdk</groupId>
            <artifactId>s3</artifactId>
            <version>2.41.14</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>io.minio</groupId>
            <artifactId>minio</artifactId>
            <version>8.6.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
            <scope>compile</scope>
        </dependency>
        <!-- Source: https://mvnrepository.com/artifact/org.jetbrains/annotations -->
        <dependency>
            <groupId>org.jetbrains</groupId>
            <artifactId>annotations</artifactId>
            <version>26.0.2-1</version>
            <scope>compile</scope>
        </dependency>
        <!-- Source: https://mvnrepository.com/artifact/jakarta.validation/jakarta.validation-api -->
        <dependency>
            <groupId>jakarta.validation</groupId>
            <artifactId>jakarta.validation-api</artifactId>
            <version>3.1.1</version>
            <scope>compile</scope>
        </dependency>
        <!-- JSON Web Token Packages -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.12.5</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.12.5</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.12.5</version>
            <scope>runtime</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

## src/main/java/com/samson/cloudstore/config/AppCorsProperties.java

```java
package com.samson.cloudstore.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.cors")
public class AppCorsProperties {
    /**
     * Example: http://localhost:3000
     */
    private List<String> allowedOrigins = new ArrayList<>();
}
```

## src/main/java/com/samson/cloudstore/config/JwtAuthFilter.java

```java
package com.samson.cloudstore.config;

import com.samson.cloudstore.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String authentication = request.getHeader("Authorization");

            if (authentication != null && authentication.startsWith("Bearer ")) {
                String token = authentication.substring(7);

                try {
                    Jws<Claims> claimsJws = jwtService.parseAndValidate(token);

                    Claims claims = claimsJws.getPayload();
                    String subject = claims.getSubject();
                    String role = Optional.ofNullable(claims.get("role")).orElse("USER").toString();
                    String username = claims.get("username", String.class);

                    MDC.put("userId", subject);
                    MDC.put("role", role);
                    MDC.put("username", username);

                    var authenticationToken = new AbstractAuthenticationToken(
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))) {
                        @Override
                        public Object getCredentials() {
                            return token;
                        }

                        @Override
                        public Object getPrincipal() {
                            return subject;
                        }
                    };

                    authenticationToken.setAuthenticated(true);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                } catch (JwtException | IllegalArgumentException | SecurityException exception) {
                    SecurityContextHolder.clearContext();
                }
            }

            filterChain.doFilter(request, response);

        } finally {
            MDC.remove("userId");
            MDC.remove("role");
            MDC.remove("username");
        }
    }
}
```

## src/main/java/com/samson/cloudstore/config/MinIOConfig.java

```java
package com.samson.cloudstore.config;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
@EnableConfigurationProperties({ MinIOProperties.class, StorageProperties.class })
@ConditionalOnProperty(prefix="app.minio", name="enabled", havingValue="true", matchIfMissing=true)
public class MinIOConfig {

    @Bean
    public MinioClient minioClient(@NonNull MinIOProperties properties) {
        return MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

}
```

## src/main/java/com/samson/cloudstore/config/MinIOProperties.java

```java
package com.samson.cloudstore.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.minio")
public class MinIOProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
```

## src/main/java/com/samson/cloudstore/config/PropertiesConfig.java

```java
package com.samson.cloudstore.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ AppCorsProperties.class })
public class PropertiesConfig {
}
```

## src/main/java/com/samson/cloudstore/config/SecurityConfig.java

```java
package com.samson.cloudstore.config;


import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // health/metrics
                        .requestMatchers("/actuator/**", "/healthz").permitAll()
                        // auth
                        .requestMatchers("/auth/login", "/auth/refresh").permitAll()
                        // user registration (if you keep it public)
                        .requestMatchers("/api/users/create").permitAll()
                        // public share resolve/download endpoints
                        .requestMatchers("/api/v1/shares/public/**", "/api/v1/shares/resolve/**", "/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(AppCorsProperties corsProps) {
        CorsConfiguration corsConfig = new CorsConfiguration();

        List<String> allowedOrigins = corsProps.getAllowedOrigins();
        corsConfig.setAllowedOrigins(allowedOrigins);

        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setExposedHeaders(List.of("Content-Disposition"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
```

## src/main/java/com/samson/cloudstore/config/StorageProperties.java

```java
package com.samson.cloudstore.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {
    /** Bucket name for object storage. */
    private String bucket;

    /** Max allowed upload size in bytes (server-side validation). */
    private Long maxFileSizeBytes;

    /** TTL for pre-signed URLs (uploads/downloads). */
    private Duration presignTtl = Duration.ofMinutes(15);

    /** Trash retention before scheduled purge. */
    private Integer trashRetentionDays = 30;

    /** Default multipart part size. */
    private Long multipartPartSizeBytes = 8L * 1024 * 1024;
}
```

## src/main/java/com/samson/cloudstore/controllers/ActivityController.java

```java
package com.samson.cloudstore.controllers;

import com.samson.cloudstore.models.AuditEvent;
import com.samson.cloudstore.services.AuditService;
import com.samson.cloudstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final AuditService auditService;
    private final UserService userService;

    @GetMapping("/api/v1/activity")
    public ResponseEntity<List<Map<String, Object>>> latest() {
        UUID userId = userService.getAuthenticatedUserId();
        List<AuditEvent> events = auditService.latestForUser(userId);

        List<Map<String, Object>> resp = events.stream().map(e -> Map.of(
                "id", e.getId(),
                "action", e.getAction(),
                "nodeId", e.getNodeId(),
                "metadata", e.getMetadata(),
                "createdAt", e.getCreatedAt()
        )).toList();

        return ResponseEntity.ok(resp);
    }
}
```

## src/main/java/com/samson/cloudstore/controllers/AuthController.java

```java
package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.AuthDtos;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.UserRepository;
import com.samson.cloudstore.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody AuthDtos.@NotNull LoginRequest requestBody) {
        Users user = userRepository.findByEmail(requestBody.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        if (!passwordEncoder.matches(requestBody.password(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");

        String accessToken = jwtService.generateAccessToken(user.getUserId(), user.getUsername(), user.getUserRole().name());
        String refreshToken = jwtService.generateRefreshToken(user.getUserId());

        return Map.of("access_token", accessToken, "refresh_token", refreshToken, "token_type", "Bearer");
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@Valid @RequestBody @NotNull Map<String, String> requestBody) {
        String refreshToken = requestBody.get("refresh_token");

        if (refreshToken == null || refreshToken.isBlank() || jwtService.isRefreshTokenExpired(refreshToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token is invalid or expired");

        Jws<Claims> claimsJws = jwtService.parseAndValidate(refreshToken);
        Claims claims = claimsJws.getPayload();

        if (!"refresh".equals(claims.get("typ", String.class)))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token is invalid");

        if (jwtService.isRefreshTokenExpired(claims.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token is expired or revoked");

        UUID userId = UUID.fromString(claims.getSubject());
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user"));

        // ROTATE: revoke old refresh token and create new one
        jwtService.revokeRefreshToken(claims.getId());
        String newAccessToken = jwtService.generateAccessToken(user.getUserId(), user.getUsername(), user.getUserRole().name());
        String newRefreshToken = jwtService.generateRefreshToken(user.getUserId());

        return Map.of("access_token", newAccessToken, "refresh_token", newRefreshToken, "token_type", "Bearer");
    }

}
```

## src/main/java/com/samson/cloudstore/controllers/RestExceptionHandler.java

```java
package com.samson.cloudstore.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage() == null ? "Bad request" : e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> serverError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Internal server error"));
    }
}
```

## src/main/java/com/samson/cloudstore/controllers/ShareController.java

```java
package com.samson.cloudstore.controllers;

import com.samson.cloudstore.models.ShareLink;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.services.ShareService;
import com.samson.cloudstore.services.UserService;
import com.samson.cloudstore.utilities.NodeType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;
    private final UserService userService;

    // -----------------
    // Authenticated API
    // -----------------

    @PostMapping("/api/v1/shares")
    public ResponseEntity<Map<String, Object>> create(@RequestBody CreateShareRequest req) {
        UUID userId = userService.getAuthenticatedUserId();

        LocalDateTime expiresAt = null;
        if (req.getExpiresAt() != null) {
            expiresAt = req.getExpiresAt();
        } else if (req.getExpiresHours() != null && req.getExpiresHours() > 0) {
            expiresAt = LocalDateTime.now().plusHours(req.getExpiresHours());
        }

        ShareLink link = shareService.createShare(
                userId,
                req.getNodeId(),
                expiresAt,
                req.getMaxDownloads(),
                req.getPassword()
        );

        return ResponseEntity.ok(Map.of(
                "id", link.getId(),
                "token", link.getToken(),
                "expiresAt", link.getExpiresAt(),
                "maxDownloads", link.getMaxDownloads(),
                "active", link.isActive()
        ));
    }

    @GetMapping("/api/v1/shares")
    public ResponseEntity<List<Map<String, Object>>> listMyShares() {
        UUID userId = userService.getAuthenticatedUserId();
        List<ShareLink> shares = shareService.listMyShares(userId);

        List<Map<String, Object>> response = shares.stream().map(s -> {
            StorageNode n = s.getNode();
            return Map.<String, Object>of(
                    "id", s.getId(),
                    "token", s.getToken(),
                    "active", s.isActive(),
                    "expiresAt", s.getExpiresAt(),
                    "maxDownloads", s.getMaxDownloads(),
                    "downloadCount", s.getDownloadCount(),
                    "node", Map.of(
                            "id", n.getId(),
                            "name", n.getName(),
                            "type", n.getType()
                    )
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/v1/shares/{id}")
    public ResponseEntity<?> revoke(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        shareService.revoke(userId, id);
        return ResponseEntity.noContent().build();
    }

    // -----------------
    // Public API (used by Next.js share page)
    // -----------------

    /**
     * Backwards-compatible endpoint used by the UI: POST /api/v1/shares/resolve/{token}
     * If the share is password-protected and no/invalid password is supplied, returns 401.
     */
    @PostMapping("/api/v1/shares/resolve/{token}")
    public ResponseEntity<?> resolveForUi(@PathVariable String token, @RequestBody(required = false) ResolveRequest req) {
        ShareLink link = shareService.requireValid(token);
        StorageNode node = link.getNode();

        if (link.getPasswordHash() != null) {
            String pwd = req == null ? null : req.getPassword();
            try {
                // this will throw if invalid
                shareService.verifyPassword(link, pwd);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Password required or invalid"));
            }
        }

        String downloadUrl = shareService.getDownloadUrl(token, req == null ? null : req.getPassword());

        return ResponseEntity.ok(Map.of(
                "name", node.getName(),
                "type", node.getType(),
                "sizeBytes", node.getSizeBytes(),
                "downloadUrl", downloadUrl
        ));
    }

    @GetMapping("/api/v1/shares/public/{token}")
    public ResponseEntity<Map<String, Object>> resolve(@PathVariable String token) {
        ShareLink link = shareService.requireValid(token);
        StorageNode n = link.getNode();

        return ResponseEntity.ok(Map.of(
                "token", link.getToken(),
                "expiresAt", link.getExpiresAt(),
                "maxDownloads", link.getMaxDownloads(),
                "downloadCount", link.getDownloadCount(),
                "passwordRequired", link.getPasswordHash() != null,
                "node", Map.of(
                        "id", n.getId(),
                        "name", n.getName(),
                        "type", n.getType(),
                        "mimeType", n.getMimeType(),
                        "sizeBytes", n.getSizeBytes()
                )
        ));
    }

    @PostMapping("/api/v1/shares/public/{token}/download-url")
    public ResponseEntity<Map<String, Object>> downloadUrl(@PathVariable String token,
                                                           @RequestBody(required = false) ResolveRequest req) {
        String password = req == null ? null : req.getPassword();
        String url = shareService.getDownloadUrl(token, password);
        return ResponseEntity.ok(Map.of("url", url));
    }

    @Data
    public static class CreateShareRequest {
        private UUID nodeId;
        // Either provide expiresAt or expiresHours.
        private LocalDateTime expiresAt;
        private Integer expiresHours;
        private Integer maxDownloads;
        private String password;
    }

    @Data
    public static class ResolveRequest {
        private String password;
    }
}
```

## src/main/java/com/samson/cloudstore/controllers/StorageNodeController.java

```java
package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.StorageNodeDto;
import com.samson.cloudstore.dto.StorageNodeMapper;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.services.StorageNodeService;
import com.samson.cloudstore.services.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/nodes")
public class StorageNodeController {

    private final StorageNodeService storageNodeService;
    private final UserService userService;

    public StorageNodeController(StorageNodeService storageNodeService, UserService userService) {
        this.storageNodeService = storageNodeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<StorageNodeDto>> list(@RequestParam(required = false) UUID parentId) {
        UUID userId = userService.getAuthenticatedUserId();
        List<StorageNodeDto> dtos = storageNodeService.listChildren(userId, parentId).stream().map(StorageNodeMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/trash")
    public ResponseEntity<List<StorageNodeDto>> listTrash() {
        UUID userId = userService.getAuthenticatedUserId();
        List<StorageNodeDto> dtos = storageNodeService.listTrashRoots(userId).stream().map(StorageNodeMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/folders")
    public ResponseEntity<StorageNodeDto> createFolder(@RequestParam(required = false) UUID parentId,
                                                       @RequestBody CreateFolderRequest request) {
        UUID userId = userService.getAuthenticatedUserId();
        UUID effectiveParentId = request.getParentId() != null ? request.getParentId() : parentId;
        StorageNode node = storageNodeService.createFolder(userId, effectiveParentId, request.getName());
        return ResponseEntity.ok(StorageNodeMapper.toDto(node));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable UUID id) {
        // Backwards-compatible: treat DELETE as move-to-trash
        UUID userId = userService.getAuthenticatedUserId();
        storageNodeService.trash(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/trash")
    public ResponseEntity<?> trash(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        storageNodeService.trash(userId, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<?> restore(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        storageNodeService.restore(userId, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/purge")
    public ResponseEntity<?> purge(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        storageNodeService.purge(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/rename")
    public ResponseEntity<StorageNodeDto> rename(@PathVariable UUID id, @RequestBody RenameRequest request) {
        UUID userId = userService.getAuthenticatedUserId();
        return ResponseEntity.ok(StorageNodeMapper.toDto(storageNodeService.rename(userId, id, request.getName())));
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<StorageNodeDto> move(@PathVariable UUID id, @RequestBody MoveRequest request) {
        UUID userId = userService.getAuthenticatedUserId();
        return ResponseEntity.ok(StorageNodeMapper.toDto(storageNodeService.move(userId, id, request.getParentId())));
    }

    @GetMapping("/{id}/download-url")
    public ResponseEntity<Map<String, String>> getDownloadUrl(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        String url = storageNodeService.getDownloadUrl(userId, id);
        return ResponseEntity.ok(Map.of("url", url));
    }

    @GetMapping("/{id}/versions")
    public ResponseEntity<?> versions(@PathVariable UUID id) {
        UUID userId = userService.getAuthenticatedUserId();
        return ResponseEntity.ok(storageNodeService.listVersions(userId, id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<StorageNodeDto>> search(@RequestParam String q) {
        UUID userId = userService.getAuthenticatedUserId();
        List<StorageNodeDto> dtos = storageNodeService.search(userId, q).stream().map(StorageNodeMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @Data
    public static class CreateFolderRequest {
        private UUID parentId;
        private String name;
    }

    @Data
    public static class RenameRequest {
        private String name;
    }

    @Data
    public static class MoveRequest {
        private UUID parentId;
    }
}
```

## src/main/java/com/samson/cloudstore/controllers/StorageUploadController.java

```java
package com.samson.cloudstore.controllers;

import com.samson.cloudstore.config.StorageProperties;
import com.samson.cloudstore.dto.StorageNodeDto;
import com.samson.cloudstore.dto.StorageNodeMapper;
import com.samson.cloudstore.dto.UploadDtos;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.services.ObjectStorageService;
import com.samson.cloudstore.services.StorageNodeService;
import com.samson.cloudstore.services.UserService;
import io.minio.StatObjectResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/uploads")
public class StorageUploadController {

    private final StorageNodeService storageNodeService;
    private final ObjectStorageService objectStorageService;
    private final UserService userService;
    private final StorageProperties storageProperties;

    public StorageUploadController(StorageNodeService storageNodeService,
                                   ObjectStorageService objectStorageService,
                                   UserService userService,
                                   StorageProperties storageProperties) {
        this.storageNodeService = storageNodeService;
        this.objectStorageService = objectStorageService;
        this.userService = userService;
        this.storageProperties = storageProperties;
    }

    @PostMapping("/initiate")
    public ResponseEntity<UploadDtos.InitiateUploadResponse> initiate(@Valid @RequestBody UploadDtos.InitiateUploadRequest request) {
        Users user = userService.getAuthenticatedUser();

        // server-side size check
        long max = storageProperties.getMaxFileSizeBytes() == null ? Long.MAX_VALUE : storageProperties.getMaxFileSizeBytes();
        if (request.sizeBytes() != null && request.sizeBytes() > max) {
            throw new IllegalArgumentException("File exceeds max size");
        }

        String objectKey = "users/" + user.getUserId() + "/" + UUID.randomUUID() + "/" + request.fileName();
        String uploadUrl = objectStorageService.generatePresignedUploadUrl(objectKey, 15);

        return ResponseEntity.ok(new UploadDtos.InitiateUploadResponse(objectKey, uploadUrl, 900));
    }

    @PostMapping("/complete")
    public ResponseEntity<StorageNodeDto> complete(@Valid @RequestBody UploadDtos.CompleteUploadRequest request) {
        Users user = userService.getAuthenticatedUser();

        StatObjectResponse stat = objectStorageService.stat(request.objectKey());

        if (request.sizeBytes() != null && stat.size() != request.sizeBytes()) {
            throw new IllegalArgumentException("Size mismatch");
        }

        StorageNode node = storageNodeService.saveUploadedFileMetadata(
                user,
                request.parentId(),
                request.fileName(),
                request.mimeType(),
                stat.size(),
                request.objectKey(),
                request.checksumSha256()
        );

        return ResponseEntity.ok(StorageNodeMapper.toDto(node));
    }
}
```

## src/main/java/com/samson/cloudstore/dto/CreateUserRequest.java

```java
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

```

## src/main/java/com/samson/cloudstore/dto/NodeDtos.java

```java
package com.samson.cloudstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.UUID;

public class NodeDtos {

    public record NodeResponse(
            UUID id,
            String name,
            String type,      // FILE or FOLDER
            String mimeType,
            Long sizeBytes,
            UUID parentId,
            OffsetDateTime updatedAt
    ) {}

    public record CreateFolderRequest(
            @NotBlank @Size(min = 1, max = 255) String name
    ) {}

    public record DownloadUrlResponse(String url) {}
}
```

## src/main/java/com/samson/cloudstore/dto/ShareDtos.java

```java
package com.samson.cloudstore.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ShareDtos {

    public record CreateShareRequest(
            @NotNull UUID nodeId,
            Integer expiresHours,
            String password,
            Integer maxDownloads
    ) {}

    public record CreateShareResponse(
            String token,
            String url
    ) {}

    public record ResolveShareRequest(
            String password
    ) {}

    public record ResolveShareResponse(
            String name,
            String type,
            Long sizeBytes,
            String downloadUrl
    ) {}
}
```

## src/main/java/com/samson/cloudstore/dto/StorageNodeDto.java

```java
package com.samson.cloudstore.dto;

import com.samson.cloudstore.utilities.NodeType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StorageNodeDto(
        UUID id,
        UUID ownerId,
        UUID parentId,
        String name,
        String path,
        NodeType type,
        String mimeType,
        Long sizeBytes,
        boolean trashed,
        OffsetDateTime trashedAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
```

## src/main/java/com/samson/cloudstore/dto/StorageNodeMapper.java

```java
package com.samson.cloudstore.dto;

import com.samson.cloudstore.models.StorageNode;

public class StorageNodeMapper {
    private StorageNodeMapper() {}

    public static StorageNodeDto toDto(StorageNode n) {
        return new StorageNodeDto(
                n.getId(),
                n.getOwnerId(),
                n.getParent() == null ? null : n.getParent().getId(),
                n.getName(),
                n.getPath(),
                n.getType(),
                n.getMimeType(),
                n.getSizeBytes(),
                n.isTrashed(),
                n.getTrashedAt(),
                n.getCreatedAt(),
                n.getUpdatedAt()
        );
    }
}
```

## src/main/java/com/samson/cloudstore/dto/UploadDtos.java

```java
package com.samson.cloudstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UploadDtos {
    public record InitiateUploadRequest(
            @NotBlank String fileName,
            @NotBlank String mimeType,
            @NotNull Long sizeBytes,
            UUID parentId
    ) {}

    public record InitiateUploadResponse(
            String objectKey,
            String uploadUrl,
            long expiresInSeconds
    ) {}

    public record CompleteUploadRequest(
            @NotBlank String objectKey,
            @NotBlank String fileName,
            @NotBlank String mimeType,
            @NotNull Long sizeBytes,
            UUID parentId,
            String checksumSha256
    ) {}
}
```

## src/main/java/com/samson/cloudstore/handlers/GlobalExceptionHandler.java

```java
package com.samson.cloudstore.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResolutionException.class)
    public ResponseEntity<Map<String, Object>> handleResolutionException(@NotNull ResponseStatusException exception) {

        assert exception.getReason() != null;

        Map<String, Object> body = Map.of(
                "status", exception.getStatusCode().value(),
                "error", exception.getReason(),
                "timestamp", Instant.now().toString()
        );

        return ResponseEntity.status(exception.getStatusCode()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(@NotNull Exception exception) {
        return ResponseEntity.status(500).body(Map.of(
                "status", 500,
                "error", exception.getMessage(),
                "timestamp", Instant.now().toString()
        ));
    }
}
```

## src/main/java/com/samson/cloudstore/loggers/StartupLogger.java

```java
package com.samson.cloudstore.loggers;

import com.samson.cloudstore.security.JWTKeyProvider;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class StartupLogger {
    private final JWTKeyProvider props;
    private final Environment environment;

    @PostConstruct
    public void logStartupInfo() {
        log.info("Active JWT kid={}, DB user={}", props.getCurrentKeyId(), environment.getProperty("spring.datasource.username"));
    }
}
```

## src/main/java/com/samson/cloudstore/models/AuditEvent.java

```java
package com.samson.cloudstore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "audit_events")
public class AuditEvent {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "action", nullable = false, length = 64)
    private String action;

    @Column(name = "node_id")
    private UUID nodeId;

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
```

## src/main/java/com/samson/cloudstore/models/FileVersion.java

```java
package com.samson.cloudstore.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "file_versions")
public class FileVersion {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "node_id", nullable = false)
    private UUID nodeId;

    @Column(name = "version_no", nullable = false)
    private Integer versionNo;

    @Column(name = "object_key", nullable = false)
    private String objectKey;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
```

## src/main/java/com/samson/cloudstore/models/IdempotencyKey.java

```java
package com.samson.cloudstore.models;

import com.samson.cloudstore.utilities.IdempotencyStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "idempotency_keys")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class IdempotencyKey {
    @Id
    @Column(name = "idem_key", nullable = false, updatable = false)
    private String key;

    @Column(name = "request_hash", nullable = false)
    private String requestHash;

    @Column(name = "status", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private IdempotencyStatus status; // IN_PROGRESS | SUCCEEDED | FAILED

    @Column(name = "resource_id")
    private UUID resourceId; // created user id

    @Column(name = "response_code")
    private Integer responseCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;
}
```

## src/main/java/com/samson/cloudstore/models/RefreshToken.java

```java
package com.samson.cloudstore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @UuidGenerator
    @Column(name = "token_id", nullable = false)
    private UUID tokenId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "jwt_id", nullable = false, unique = true)
    private String jwtId;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
```

## src/main/java/com/samson/cloudstore/models/ShareLink.java

```java
package com.samson.cloudstore.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "share_links")
public class ShareLink {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "node_id", nullable = false)
    private StorageNode node;

    @Column(name = "token", unique = true, nullable = false, length = 128)
    private String token;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "max_downloads")
    private Integer maxDownloads;

    @Column(name = "download_count", nullable = false)
    private int downloadCount;

    @Column(name = "password_hash")
    private String passwordHash;
}
```

## src/main/java/com/samson/cloudstore/models/StorageNode.java

```java
package com.samson.cloudstore.models;

import com.samson.cloudstore.utilities.NodeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "storage_nodes")
public class StorageNode {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private StorageNode parent;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Path is a denormalized helper for fast prefix queries. It is updated on rename/move.
     * Example: /My Drive/Photos/2026
     */
    @Column(name = "path", nullable = false)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private NodeType type;

    // File fields
    @Column(name = "object_key")
    private String objectKey;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;

    @Column(name = "is_trashed", nullable = false)
    private boolean trashed;

    @Column(name = "trashed_at")
    private OffsetDateTime trashedAt;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private long version;
}
```

## src/main/java/com/samson/cloudstore/models/Users.java

```java
package com.samson.cloudstore.models;

import com.samson.cloudstore.utilities.UserRole;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @UuidGenerator
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "hashed_password", nullable = false, length = 72)
    private String hashedPassword;

    /**
     * Store as VARCHAR to avoid PostgreSQL native enum DDL churn.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 16)
    private UserRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private long version;
}
```

## src/main/java/com/samson/cloudstore/repositories/AuditEventRepository.java

```java
package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {
    List<AuditEvent> findTop50ByUserIdOrderByCreatedAtDesc(UUID userId);
}
```

## src/main/java/com/samson/cloudstore/repositories/FileVersionRepository.java

```java
package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FileVersionRepository extends JpaRepository<FileVersion, UUID> {

    @Query("select coalesce(max(v.versionNo), 0) from FileVersion v where v.nodeId = :nodeId")
    int findMaxVersionNo(@Param("nodeId") UUID nodeId);

    List<FileVersion> findByNodeIdOrderByVersionNoDesc(UUID nodeId);
}
```

## src/main/java/com/samson/cloudstore/repositories/RefreshTokenRepository.java

```java
package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByJwtId(String jwtId);

    long deleteByExpiresAtBefore(OffsetDateTime now);

    long deleteByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query("update RefreshToken r set r.revoked = true where r.userId = :userId")
    int revokeAllForUser(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query("update RefreshToken r set r.revoked = true where r.userId = :userId and r.revoked = false")
    int revokeActiveForUser(@Param("userId") UUID userId);
}
```

## src/main/java/com/samson/cloudstore/repositories/ShareLinkRepository.java

```java
package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.ShareLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShareLinkRepository extends JpaRepository<ShareLink, UUID> {

    Optional<ShareLink> findByToken(String token);

    List<ShareLink> findAllByNodeOwnerIdOrderByCreatedAtDesc(UUID ownerId);

    @Query("select s from ShareLink s where s.expiresAt is not null and s.expiresAt < :now")
    List<ShareLink> findExpired(@Param("now") LocalDateTime now);
}
```

## src/main/java/com/samson/cloudstore/repositories/StorageNodeRepository.java

```java
package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.utilities.NodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StorageNodeRepository extends JpaRepository<StorageNode, UUID> {

    List<StorageNode> findByOwnerIdAndParent_IdAndTrashedFalseOrderByTypeAscNameAsc(UUID ownerId, UUID parentId);

    List<StorageNode> findByOwnerIdAndParentIsNullAndTrashedFalseOrderByTypeAscNameAsc(UUID ownerId);

    List<StorageNode> findByOwnerIdAndParent_IdAndTrashedTrueOrderByTypeAscNameAsc(UUID ownerId, UUID parentId);

    List<StorageNode> findByOwnerIdAndParentIsNullAndTrashedTrueOrderByTypeAscNameAsc(UUID ownerId);

    Optional<StorageNode> findByIdAndOwnerId(UUID id, UUID ownerId);

    boolean existsByOwnerIdAndParentIsNullAndNameIgnoreCaseAndTrashedFalse(UUID ownerId, String name);

    boolean existsByOwnerIdAndParent_IdAndNameIgnoreCaseAndTrashedFalse(UUID ownerId, UUID parentId, String name);

    @Query("select n from StorageNode n where n.ownerId = :ownerId and n.trashed = true and (n.parent is null or n.parent.trashed = false) order by n.updatedAt desc")
    List<StorageNode> findTrashRoots(@Param("ownerId") UUID ownerId);
    @Query("select n from StorageNode n where n.ownerId = :ownerId and n.trashed = true and n.trashedAt is not null and n.trashedAt < :cutoff and (n.parent is null or n.parent.trashed = false) order by n.trashedAt asc")
    List<StorageNode> findTrashRootsOlderThan(@Param("ownerId") UUID ownerId, @Param("cutoff") java.time.OffsetDateTime cutoff);



    @Query("select n from StorageNode n where n.ownerId = :ownerId and n.trashed = false and lower(n.name) like lower(concat('%', :query, '%')) order by n.type asc, n.name asc")
    List<StorageNode> search(@Param("ownerId") UUID ownerId, @Param("query") String query);

    @Query("select coalesce(sum(n.sizeBytes), 0) from StorageNode n where n.ownerId = :ownerId and n.trashed = false and n.type = :type")
    long sumSizeByOwnerAndTypeNotTrashed(@Param("ownerId") UUID ownerId, @Param("type") NodeType type);

    long countByOwnerIdAndTrashedTrue(UUID ownerId);

    long countByOwnerIdAndTrashedFalse(UUID ownerId);

    @Query("select n from StorageNode n where n.trashed = true and n.trashedAt is not null and n.trashedAt < :cutoff and (n.parent is null or n.parent.trashed = false) order by n.trashedAt asc")
    List<StorageNode> findAllTrashRootsOlderThan(@Param("cutoff") java.time.OffsetDateTime cutoff);


}
```

## src/main/java/com/samson/cloudstore/repositories/UserRepository.java

```java
package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailOrUsername(String email, String username);

    // Optional<Users> findUsersByUser_id(UUID id);

    Optional<Users> findByEmailIgnoreCase(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
```

## src/main/java/com/samson/cloudstore/security/RotatingKeysProperties.java

```java
package com.samson.cloudstore.security;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "jwt.rotating-keys")
@Validated
@Getter @Setter
public class RotatingKeysProperties {

    @NotBlank private String issuer;

    @NotEmpty @Valid
    private List<KeySpecification> keySpecificationList = new ArrayList<>();

    @NotBlank private String currentKeyId;

    // JWTKeyProvider.parseTtl() expects formats like: 900, 15m, 2h, 7d
    @NotBlank private String accessTtl = "15m";

    @NotBlank private String refreshTtl = "7d";

    @Getter @Setter
    public static class KeySpecification {
        @NotBlank private String keyId;

        @NotBlank private String secretBase64;
    }
}
```

## src/main/java/com/samson/cloudstore/services/AuditService.java

```java
package com.samson.cloudstore.services;

import com.samson.cloudstore.models.AuditEvent;
import com.samson.cloudstore.repositories.AuditEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditEventRepository auditEventRepository;

    public void log(UUID userId, String action, UUID nodeId, String metadataJson) {
        AuditEvent ev = AuditEvent.builder()
                .userId(userId)
                .action(action)
                .nodeId(nodeId)
                .metadata(metadataJson)
                .createdAt(OffsetDateTime.now())
                .build();
        auditEventRepository.save(ev);
    }

    public List<AuditEvent> latestForUser(UUID userId) {
        return auditEventRepository.findTop50ByUserIdOrderByCreatedAtDesc(userId);
    }
}
```

## src/main/java/com/samson/cloudstore/services/CleanupScheduler.java

```java
package com.samson.cloudstore.services;

import com.samson.cloudstore.config.StorageProperties;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.repositories.IdempotencyRepository;
import com.samson.cloudstore.repositories.RefreshTokenRepository;
import com.samson.cloudstore.repositories.StorageNodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanupScheduler {

    private final StorageProperties storageProperties;
    private final StorageNodeRepository storageNodeRepository;
    private final StorageNodeService storageNodeService;
    private final ShareService shareService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final IdempotencyRepository idempotencyRepository;

    // Every day at 03:30
    @Scheduled(cron = "0 30 3 * * *")
    public void dailyCleanup() {
        purgeExpiredShares();
        purgeExpiredRefreshTokens();
        purgeOldIdempotencyKeys();
        purgeOldTrash();
    }

    // Also run every 30 minutes for faster feedback in dev
    @Scheduled(fixedDelay = 30 * 60 * 1000L)
    public void frequentDevCleanup() {
        purgeExpiredRefreshTokens();
        purgeOldIdempotencyKeys();
    }

    private void purgeExpiredShares() {
        try {
            int count = shareService.purgeExpired();
            if (count > 0) log.info("Deactivated {} expired share links", count);
        } catch (Exception e) {
            log.warn("Share cleanup failed", e);
        }
    }

    private void purgeExpiredRefreshTokens() {
        try {
            long deleted = refreshTokenRepository.deleteByExpiresAtBefore(OffsetDateTime.now());
            if (deleted > 0) log.info("Deleted {} expired refresh tokens", deleted);
        } catch (Exception e) {
            log.warn("Refresh token cleanup failed", e);
        }
    }

    private void purgeOldIdempotencyKeys() {
        try {
            // keep idempotency keys 7 days
            OffsetDateTime cutoff = OffsetDateTime.now().minus(7, ChronoUnit.DAYS);
            long deleted = idempotencyRepository.deleteByCreatedAtBefore(cutoff);
            if (deleted > 0) log.info("Deleted {} old idempotency keys", deleted);
        } catch (Exception e) {
            log.warn("Idempotency cleanup failed", e);
        }
    }

    private void purgeOldTrash() {
        try {
            int retentionDays = storageProperties.getTrashRetentionDays() == null ? 30 : storageProperties.getTrashRetentionDays();
            OffsetDateTime cutoff = OffsetDateTime.now().minus(retentionDays, ChronoUnit.DAYS);

            List<StorageNode> roots = storageNodeRepository.findAllTrashRootsOlderThan(cutoff);
            if (roots.isEmpty()) return;

            int purged = 0;
            for (StorageNode r : roots) {
                try {
                    storageNodeService.purge(r.getOwnerId(), r.getId());
                    purged++;
                } catch (Exception e) {
                    log.warn("Failed to purge trashed node {}", r.getId(), e);
                }
            }

            if (purged > 0) log.info("Purged {} trash roots older than {} days", purged, retentionDays);
        } catch (Exception e) {
            log.warn("Trash purge failed", e);
        }
    }

}
```

## src/main/java/com/samson/cloudstore/services/CurrentUserService.java

```java
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
```

## src/main/java/com/samson/cloudstore/services/JwtService.java

```java
package com.samson.cloudstore.services;

import com.samson.cloudstore.models.RefreshToken;
import com.samson.cloudstore.repositories.RefreshTokenRepository;
import com.samson.cloudstore.security.JWTKeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final JWTKeyProvider keyProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public JwtService(JWTKeyProvider keyProvider, RefreshTokenRepository refreshTokenRepository) {
        this.keyProvider = keyProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateAccessToken(@NonNull UUID userId, String username, String role) {
        OffsetDateTime now = OffsetDateTime.now();

        Date iat = Date.from(now.toInstant());
        Date exp = Date.from(now.plus(keyProvider.getAccessTtl()).toInstant());

        Key key = keyProvider.getKeysByKeyID().get(keyProvider.getCurrentKeyId());

        return Jwts.builder()
                .header().add("kid", keyProvider.getCurrentKeyId()).and()
                .subject(userId.toString())
                .issuer(keyProvider.getIssuer())
                .issuedAt(iat)
                .expiration(exp)
                .claim("username", username)
                .claim("role", role)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(@NonNull UUID userId) {
        OffsetDateTime now = OffsetDateTime.now();

        Date iat = Date.from(now.toInstant());
        Date exp = Date.from(now.plus(keyProvider.getRefreshTtl()).toInstant());

        String jwtId = UUID.randomUUID().toString();
        Key key = keyProvider.getKeysByKeyID().get(keyProvider.getCurrentKeyId());

        String token = Jwts.builder()
                .header().add("kid", keyProvider.getCurrentKeyId()).and()
                .subject(userId.toString())
                .issuer(keyProvider.getIssuer())
                .id(jwtId)
                .issuedAt(iat).expiration(exp)
                .claim("typ", "refresh")
                .signWith(key)
                .compact();

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .userId(userId)
                        .jwtId(jwtId)
                        .expiresAt(OffsetDateTime.ofInstant(exp.toInstant(), now.getOffset()))
                        .revoked(false).build()
        );

        return token;
    }

    public Jws<Claims> validateAccessToken(@NonNull String token) {
        return Jwts.parser().verifyWith((SecretKey) keyProvider.getKeysByKeyID().get(keyProvider.getCurrentKeyId())).build().parseSignedClaims(token);
    }

    public Jws<Claims> parseAndValidate(String jwt) {
        return Jwts.parser().keyLocator(header -> {
            Object kidObj = header.get("kid");
            String keyId = (kidObj != null && !kidObj.toString().isBlank())
                    ? kidObj.toString()
                    : keyProvider.getCurrentKeyId(); // fallback

            Key key = keyProvider.getKeysByKeyID().get(keyId);
            if (key == null) throw new SecurityException("Unknown Key ID: " + keyId);

            return key;
        })
        .build()
        .parseSignedClaims(jwt);
    }

    public Boolean isRefreshTokenExpired(String jwtId) {
        return refreshTokenRepository.findByJwtId(jwtId)
                .map(refreshToken -> refreshToken.getRevoked() || refreshToken.getExpiresAt().isBefore(OffsetDateTime.now()))
                .orElse(true);
    }

    public void revokeRefreshToken(String jwtId) {
        refreshTokenRepository.findByJwtId(jwtId).ifPresent(refreshToken -> {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
        });
    }

}
```

## src/main/java/com/samson/cloudstore/services/ObjectStorageService.java

```java
package com.samson.cloudstore.services;

import com.samson.cloudstore.config.StorageProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.ComposeSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;

@Service
public class ObjectStorageService {

    private final MinioClient minioClient;
    private final StorageProperties storageProperties;

    public ObjectStorageService(MinioClient minioClient, StorageProperties storageProperties) {
        this.minioClient = minioClient;
        this.storageProperties = storageProperties;
    }

    public void ensureBucketExists() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(storageProperties.getBucket()).build()
            );
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(storageProperties.getBucket()).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to ensure bucket exists", e);
        }
    }

    public void uploadObject(String objectKey, InputStream inputStream, long size, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .stream(inputStream, size, -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Upload to object storage failed", e);
        }
    }

    public String createPresignedPutUrl(String objectKey, String contentType) {
        try {
            int expiresSeconds = ttlSeconds(storageProperties.getPresignTtl());
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .expiry(expiresSeconds)                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create presigned PUT URL", e);
        }
    }

    public String createPresignedGetUrl(String objectKey) {
        try {
            int expiresSeconds = ttlSeconds(storageProperties.getPresignTtl());
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .expiry(expiresSeconds)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create presigned GET URL", e);
        }
    }

    public void deleteObject(String objectKey) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete object", e);
        }
    }

    /**
     * Compose (server-side concatenate) parts into a single object.
     * Requires that each part was uploaded as an object in the same bucket.
     */
    public void composeObject(String targetKey, List<String> partKeys) {
        try {
            List<ComposeSource> sources = partKeys.stream()
                    .map(k -> ComposeSource.builder().bucket(storageProperties.getBucket()).object(k).build())
                    .toList();

            minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(storageProperties.getBucket())
                            .object(targetKey)
                            .sources(sources)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to compose object", e);
        }
    }

    private int ttlSeconds(Duration ttl) {
        if (ttl == null) return 900;
        long s = ttl.getSeconds();
        if (s < 1) return 1;
        if (s > 60L * 60 * 24 * 7) return (int) (60L * 60 * 24 * 7); // cap at 7 days
        return (int) s;
    }
}

    // ---------------------------
    // Backwards-compatible helpers
    // ---------------------------

    /**
     * Previous API used by controllers.
     */
    public String generatePresignedUploadUrl(String objectKey, int expiryMinutes) {
        Duration ttl = Duration.ofMinutes(Math.max(1, expiryMinutes));
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .expiry(ttlSeconds(ttl))
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create presigned upload URL", e);
        }
    }

    public String generatePresignedDownloadUrl(String objectKey, int expiryMinutes) {
        Duration ttl = Duration.ofMinutes(Math.max(1, expiryMinutes));
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .expiry(ttlSeconds(ttl))
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create presigned download URL", e);
        }
    }

    public io.minio.StatObjectResponse stat(String objectKey) {
        try {
            return minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to stat object", e);
        }
    }
}
```

## src/main/java/com/samson/cloudstore/services/ShareService.java

```java
package com.samson.cloudstore.services;

import com.samson.cloudstore.models.ShareLink;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.repositories.ShareLinkRepository;
import com.samson.cloudstore.repositories.StorageNodeRepository;
import com.samson.cloudstore.utilities.Hashing;
import com.samson.cloudstore.utilities.NodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShareService {

    private static final SecureRandom RNG = new SecureRandom();

    private final ShareLinkRepository shareLinkRepository;
    private final StorageNodeRepository storageNodeRepository;
    private final ObjectStorageService objectStorageService;

    public ShareLink createShare(UUID ownerId,
                                UUID nodeId,
                                LocalDateTime expiresAt,
                                Integer maxDownloads,
                                String password) {

        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (node.getType() != NodeType.FILE) {
            throw new IllegalArgumentException("Only FILE nodes can be shared");
        }
        if (node.isTrashed()) {
            throw new IllegalArgumentException("Cannot share trashed nodes");
        }

        String token = generateToken();
        String passwordHash = (password == null || password.isBlank()) ? null : Hashing.hashPassword(password);

        ShareLink shareLink = ShareLink.builder()
                .node(node)
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(expiresAt)
                .active(true)
                .maxDownloads(maxDownloads)
                .downloadCount(0)
                .passwordHash(passwordHash)
                .build();

        return shareLinkRepository.save(shareLink);
    }

    public ShareLink requireValid(String token) {
        ShareLink link = shareLinkRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid share token"));

        if (!link.isActive()) throw new IllegalArgumentException("Share link inactive");
        if (link.getExpiresAt() != null && link.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Share link expired");
        }
        if (link.getMaxDownloads() != null && link.getDownloadCount() >= link.getMaxDownloads()) {
            throw new IllegalArgumentException("Share link download limit reached");
        }
        return link;
    }

    public boolean isPasswordRequired(String token) {
        ShareLink link = shareLinkRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid share token"));
        return link.getPasswordHash() != null;
    }

    public void verifyPassword(ShareLink link, String password) {
        if (link.getPasswordHash() == null) return;
        if (password == null || password.isBlank() || !Hashing.verifyPassword(password, link.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    @Transactional
    public String getDownloadUrl(String token, String password) {
        ShareLink link = requireValid(token);
        verifyPassword(link, password);

        StorageNode node = link.getNode();
        if (node.isTrashed()) {
            throw new IllegalArgumentException("Shared file is no longer available");
        }

        // increment before generating (best-effort)
        link.setDownloadCount(link.getDownloadCount() + 1);
        shareLinkRepository.save(link);

        return objectStorageService.createPresignedGetUrl(node.getObjectKey());
    }

    public List<ShareLink> listMyShares(UUID ownerId) {
        return shareLinkRepository.findAllByNodeOwnerIdOrderByCreatedAtDesc(ownerId);
    }

    @Transactional
    public void revoke(UUID ownerId, UUID shareId) {
        ShareLink link = shareLinkRepository.findById(shareId)
                .orElseThrow(() -> new IllegalArgumentException("Share not found"));

        if (!link.getNode().getOwnerId().equals(ownerId)) {
            throw new IllegalArgumentException("Not allowed");
        }

        link.setActive(false);
        shareLinkRepository.save(link);
    }

    @Transactional
    public int purgeExpired() {
        List<ShareLink> expired = shareLinkRepository.findExpired(LocalDateTime.now());
        expired.forEach(l -> l.setActive(false));
        shareLinkRepository.saveAll(expired);
        return expired.size();
    }

    private static String generateToken() {
        byte[] bytes = new byte[24];
        RNG.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
```

## src/main/java/com/samson/cloudstore/services/StorageNodeService.java

```java
package com.samson.cloudstore.services;

import com.samson.cloudstore.models.FileVersion;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.FileVersionRepository;
import com.samson.cloudstore.repositories.StorageNodeRepository;
import com.samson.cloudstore.utilities.NodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StorageNodeService {

    private final StorageNodeRepository storageNodeRepository;
    private final FileVersionRepository fileVersionRepository;
    private final ObjectStorageService objectStorageService;
    private final AuditService auditService;

    // ------------------
    // Listing
    // ------------------

    public List<StorageNode> listChildren(UUID ownerId, UUID parentId) {
        if (parentId == null) {
            return storageNodeRepository.findByOwnerIdAndParentIsNullAndTrashedFalseOrderByTypeAscNameAsc(ownerId);
        }
        return storageNodeRepository.findByOwnerIdAndParent_IdAndTrashedFalseOrderByTypeAscNameAsc(ownerId, parentId);
    }

    public List<StorageNode> search(UUID ownerId, String query) {
        if (query == null || query.isBlank()) return List.of();
        return storageNodeRepository.search(ownerId, query.trim());
    }

    public List<StorageNode> listTrashRoots(UUID ownerId) {
        return storageNodeRepository.findTrashRoots(ownerId);
    }

    // ------------------
    // Create
    // ------------------

    @Transactional
    public StorageNode createFolder(UUID ownerId, UUID parentId, String name) {
        Objects.requireNonNull(ownerId, "ownerId");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Folder name required");

        StorageNode parent = resolveFolderParent(ownerId, parentId);
        ensureNoNameConflict(ownerId, parentId, name);

        OffsetDateTime now = OffsetDateTime.now();
        StorageNode node = StorageNode.builder()
                .ownerId(ownerId)
                .parent(parent)
                .name(name)
                .type(NodeType.FOLDER)
                .path(buildPath(parent, name))
                .trashed(false)
                .createdAt(now)
                .updatedAt(now)
                .version(0)
                .build();

        StorageNode saved = storageNodeRepository.save(node);
        auditService.log(ownerId, "NODE_CREATE_FOLDER", saved.getId(), json("name", name));
        return saved;
    }

    /** Convenience overload for existing controllers. */
    @Transactional
    public StorageNode saveUploadedFileMetadata(Users user,
                                               UUID parentId,
                                               String fileName,
                                               String mimeType,
                                               long sizeBytes,
                                               String objectKey,
                                               String checksumSha256) {
        return saveUploadedFileMetadata(user.getUserId(), parentId, fileName, mimeType, sizeBytes, objectKey, checksumSha256);
    }


    /**
     * Called when an object has been uploaded to MinIO using a presigned URL.
     * If a file with the same name exists in the same folder, we create a new version.
     */
    @Transactional
    public StorageNode saveUploadedFileMetadata(UUID ownerId,
                                               UUID parentId,
                                               String fileName,
                                               String mimeType,
                                               long sizeBytes,
                                               String objectKey,
                                               String checksumSha256) {

        Objects.requireNonNull(ownerId, "ownerId");
        if (fileName == null || fileName.isBlank()) throw new IllegalArgumentException("File name required");
        if (objectKey == null || objectKey.isBlank()) throw new IllegalArgumentException("objectKey required");

        StorageNode parent = resolveFolderParent(ownerId, parentId);
        OffsetDateTime now = OffsetDateTime.now();

        // Overwrite-as-new-version for same-name file
        Optional<StorageNode> existingOpt = findSiblingByName(ownerId, parentId, fileName);
        if (existingOpt.isPresent()) {
            StorageNode existing = existingOpt.get();
            if (existing.getType() != NodeType.FILE) {
                throw new IllegalArgumentException("A folder with that name already exists");
            }

            // create version snapshot of existing
            int nextVersion = fileVersionRepository.findMaxVersionNo(existing.getId()) + 1;
            FileVersion version = FileVersion.builder()
                    .nodeId(existing.getId())
                    .versionNo(nextVersion)
                    .objectKey(existing.getObjectKey())
                    .sizeBytes(existing.getSizeBytes() == null ? 0L : existing.getSizeBytes())
                    .mimeType(existing.getMimeType())
                    .checksumSha256(existing.getChecksumSha256())
                    .createdAt(now)
                    .build();
            fileVersionRepository.save(version);

            existing.setObjectKey(objectKey);
            existing.setMimeType(mimeType);
            existing.setSizeBytes(sizeBytes);
            existing.setChecksumSha256(checksumSha256);
            existing.setUpdatedAt(now);
            StorageNode saved = storageNodeRepository.save(existing);

            auditService.log(ownerId, "NODE_UPLOAD_VERSION", saved.getId(), json("version", String.valueOf(nextVersion)));
            return saved;
        }

        // new file
        StorageNode node = StorageNode.builder()
                .ownerId(ownerId)
                .parent(parent)
                .name(fileName)
                .type(NodeType.FILE)
                .path(buildPath(parent, fileName))
                .objectKey(objectKey)
                .sizeBytes(sizeBytes)
                .mimeType(mimeType)
                .checksumSha256(checksumSha256)
                .trashed(false)
                .createdAt(now)
                .updatedAt(now)
                .version(0)
                .build();

        StorageNode saved = storageNodeRepository.save(node);
        auditService.log(ownerId, "NODE_UPLOAD_CREATE", saved.getId(), null);
        return saved;
    }

    public List<FileVersion> listVersions(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));
        if (node.getType() != NodeType.FILE) throw new IllegalArgumentException("Only files have versions");
        return fileVersionRepository.findByNodeIdOrderByVersionNoDesc(nodeId);
    }

    // ------------------
    // Download
    // ------------------

    public String getDownloadUrl(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (node.isTrashed()) throw new IllegalArgumentException("Node is trashed");
        if (node.getType() != NodeType.FILE) throw new IllegalArgumentException("Only files can be downloaded");

        auditService.log(ownerId, "NODE_DOWNLOAD_URL", node.getId(), null);
        return objectStorageService.createPresignedGetUrl(node.getObjectKey());
    }

    // ------------------
    // Trash / Restore
    // ------------------

    @Transactional
    public void trash(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (node.isTrashed()) return;

        OffsetDateTime now = OffsetDateTime.now();
        markSubtreeTrashed(node, now);
        auditService.log(ownerId, "NODE_TRASH", nodeId, null);
    }

    @Transactional
    public void restore(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (!node.isTrashed()) return;

        // if parent is trashed, restore to root
        StorageNode parent = node.getParent();
        if (parent != null && parent.isTrashed()) {
            node.setParent(null);
            node.setPath(buildPath(null, node.getName()));
        }

        untrashSubtree(node);
        updatePathsRecursively(node);
        auditService.log(ownerId, "NODE_RESTORE", nodeId, null);
    }

    // Permanently delete a trashed node and its subtree
    @Transactional
    public void purge(UUID ownerId, UUID nodeId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (!node.isTrashed()) {
            throw new IllegalArgumentException("Node must be trashed before purge");
        }

        List<StorageNode> subtree = collectSubtree(node);

        // Delete objects for current files
        for (StorageNode n : subtree) {
            if (n.getType() == NodeType.FILE && n.getObjectKey() != null) {
                try {
                    objectStorageService.deleteObject(n.getObjectKey());
                } catch (Exception ignored) {
                }
            }
        }

        // Delete version objects too
        for (StorageNode n : subtree) {
            if (n.getType() == NodeType.FILE) {
                List<FileVersion> versions = fileVersionRepository.findByNodeIdOrderByVersionNoDesc(n.getId());
                for (FileVersion v : versions) {
                    try {
                        objectStorageService.deleteObject(v.getObjectKey());
                    } catch (Exception ignored) {
                    }
                }
                fileVersionRepository.deleteAll(versions);
            }
        }

        subtree.sort(Comparator.comparingInt(this::depth).reversed());
        storageNodeRepository.deleteAll(subtree);

        auditService.log(ownerId, "NODE_PURGE", nodeId, json("count", String.valueOf(subtree.size())));
    }

    // ------------------
    // Move / Rename
    // ------------------

    @Transactional
    public StorageNode rename(UUID ownerId, UUID nodeId, String newName) {
        if (newName == null || newName.isBlank()) throw new IllegalArgumentException("Name required");

        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));
        if (node.isTrashed()) throw new IllegalArgumentException("Cannot rename trashed node");

        UUID parentId = node.getParent() == null ? null : node.getParent().getId();
        if (!node.getName().equalsIgnoreCase(newName)) {
            ensureNoNameConflict(ownerId, parentId, newName);
        }

        node.setName(newName);
        node.setPath(buildPath(node.getParent(), newName));
        node.setUpdatedAt(OffsetDateTime.now());

        StorageNode saved = storageNodeRepository.save(node);
        updatePathsRecursively(saved);

        auditService.log(ownerId, "NODE_RENAME", nodeId, json("name", newName));
        return saved;
    }

    @Transactional
    public StorageNode move(UUID ownerId, UUID nodeId, UUID newParentId) {
        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));
        if (node.isTrashed()) throw new IllegalArgumentException("Cannot move trashed node");

        StorageNode newParent = resolveFolderParent(ownerId, newParentId);

        // cycle check for folders
        if (newParent != null && node.getType() == NodeType.FOLDER) {
            StorageNode cursor = newParent;
            while (cursor != null) {
                if (cursor.getId().equals(node.getId())) {
                    throw new IllegalArgumentException("Cannot move a folder into itself (cycle)");
                }
                cursor = cursor.getParent();
            }
        }

        UUID parentId = newParent == null ? null : newParent.getId();
        ensureNoNameConflict(ownerId, parentId, node.getName());

        node.setParent(newParent);
        node.setPath(buildPath(newParent, node.getName()));
        node.setUpdatedAt(OffsetDateTime.now());

        StorageNode saved = storageNodeRepository.save(node);
        updatePathsRecursively(saved);

        auditService.log(ownerId, "NODE_MOVE", nodeId, json("newParentId", String.valueOf(newParentId)));
        return saved;
    }

    // ------------------
    // Stats
    // ------------------

    public Map<String, Object> getUserStats(UUID ownerId) {
        long usedBytes = storageNodeRepository.sumSizeByOwnerAndTypeNotTrashed(ownerId, NodeType.FILE);
        long activeCount = storageNodeRepository.countByOwnerIdAndTrashedFalse(ownerId);
        long trashCount = storageNodeRepository.countByOwnerIdAndTrashedTrue(ownerId);
        return Map.of(
                "usedBytes", usedBytes,
                "activeCount", activeCount,
                "trashCount", trashCount
        );
    }

    // ------------------
    // Helpers
    // ------------------

    private StorageNode resolveFolderParent(UUID ownerId, UUID parentId) {
        if (parentId == null) return null;
        StorageNode parent = storageNodeRepository.findByIdAndOwnerId(parentId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));
        if (parent.isTrashed()) throw new IllegalArgumentException("Parent is trashed");
        if (parent.getType() != NodeType.FOLDER) throw new IllegalArgumentException("Parent must be a folder");
        return parent;
    }

    private void ensureNoNameConflict(UUID ownerId, UUID parentId, String name) {
        boolean exists = (parentId == null)
                ? storageNodeRepository.existsByOwnerIdAndParentIsNullAndNameIgnoreCaseAndTrashedFalse(ownerId, name)
                : storageNodeRepository.existsByOwnerIdAndParent_IdAndNameIgnoreCaseAndTrashedFalse(ownerId, parentId, name);

        if (exists) throw new IllegalArgumentException("A node with that name already exists in the target folder");
    }

    private Optional<StorageNode> findSiblingByName(UUID ownerId, UUID parentId, String name) {
        List<StorageNode> siblings = listChildren(ownerId, parentId);
        return siblings.stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();
    }

    private String buildPath(StorageNode parent, String name) {
        if (parent == null) {
            return "/" + sanitize(name);
        }
        String p = parent.getPath();
        if (p == null || p.isBlank()) p = "/";
        if ("/".equals(p)) {
            return "/" + sanitize(name);
        }
        return p + "/" + sanitize(name);
    }

    private String sanitize(String segment) {
        return segment.trim().replace("/", "-");
    }

    private void markSubtreeTrashed(StorageNode root, OffsetDateTime now) {
        root.setTrashed(true);
        root.setTrashedAt(now);
        root.setUpdatedAt(now);
        storageNodeRepository.save(root);

        for (StorageNode child : childrenOf(root)) {
            if (!child.isTrashed()) {
                markSubtreeTrashed(child, now);
            }
        }
    }

    private void untrashSubtree(StorageNode root) {
        OffsetDateTime now = OffsetDateTime.now();
        root.setTrashed(false);
        root.setTrashedAt(null);
        root.setUpdatedAt(now);
        storageNodeRepository.save(root);

        for (StorageNode child : childrenOf(root)) {
            if (child.isTrashed()) {
                untrashSubtree(child);
            }
        }
    }

    private List<StorageNode> childrenOf(StorageNode parent) {
        UUID ownerId = parent.getOwnerId();
        UUID parentId = parent.getId();

        List<StorageNode> notTrashed = storageNodeRepository.findByOwnerIdAndParent_IdAndTrashedFalseOrderByTypeAscNameAsc(ownerId, parentId);
        List<StorageNode> trashed = storageNodeRepository.findByOwnerIdAndParent_IdAndTrashedTrueOrderByTypeAscNameAsc(ownerId, parentId);

        List<StorageNode> all = new ArrayList<>(notTrashed.size() + trashed.size());
        all.addAll(notTrashed);
        all.addAll(trashed);
        return all;
    }

    private void updatePathsRecursively(StorageNode root) {
        for (StorageNode child : childrenOf(root)) {
            child.setPath(buildPath(root, child.getName()));
            child.setUpdatedAt(OffsetDateTime.now());
            storageNodeRepository.save(child);
            updatePathsRecursively(child);
        }
    }

    private List<StorageNode> collectSubtree(StorageNode root) {
        List<StorageNode> result = new ArrayList<>();
        Deque<StorageNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            StorageNode current = stack.pop();
            result.add(current);
            for (StorageNode child : childrenOf(current)) {
                stack.push(child);
            }
        }
        return result;
    }

    private int depth(StorageNode node) {
        int d = 0;
        StorageNode p = node.getParent();
        while (p != null) {
            d++;
            p = p.getParent();
        }
        return d;
    }

    private String json(String k, String v) {
        if (k == null) return null;
        return "{\"" + escape(k) + "\":\"" + escape(v) + "\"}";
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
```

## src/main/java/com/samson/cloudstore/services/UserService.java

```java
package com.samson.cloudstore.services;

import com.samson.cloudstore.dto.CreateUserRequest;
import com.samson.cloudstore.dto.UserResponse;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    public UserResponse get(UUID id) {
        Users u = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        return new UserResponse(u.getUserId().toString(), u.getUsername(), u.getEmail(), u.getUserRole().name());
    }

    @Transactional(readOnly = true)
    public Iterable<UserResponse> getAllUsers() {
        return repository.findAll().stream().map(
                users -> new UserResponse(
                        users.getUserId().toString(), users.getUsername(),
                        users.getEmail(), users.getUserRole().name()
                )
        ).toList();
    }

    @Transactional
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
}
```

## src/main/java/com/samson/cloudstore/utilities/Hashing.java

```java
package com.samson.cloudstore.utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public final class Hashing {
    private Hashing() {}

    private static String SHA256(String input) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(digest);

        } catch (Exception e) {
            throw new IllegalStateException("Error computing SHA-256 hash", e);
        }
    }
}
```

## src/main/java/com/samson/cloudstore/utilities/IdempotencyStatus.java

```java
package com.samson.cloudstore.utilities;

public enum IdempotencyStatus {
    IN_PROGRESS, SUCCESS, FAILED
}
```

## src/main/java/com/samson/cloudstore/utilities/NodeType.java

```java
package com.samson.cloudstore.utilities;

public enum NodeType {
    FILE, FOLDER
}
```

## src/main/java/com/samson/cloudstore/utilities/UserRole.java

```java
package com.samson.cloudstore.utilities;

public enum UserRole {
    ADMIN, USER
}
```

## src/main/resources/application.properties

```properties
spring.application.name=cloud-store
server.port=8080
server.tomcat.connection-timeout=10000
spring.jpa.open-in-view=false
server.shutdown=graceful
logging.level.org.springframework=INFO
logging.level.com.samson.cloudstore=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%X{requestId}] [user:%X{username}:%X{userId}] %logger{36} - %msg%n

# =========================
# Database Configs (PostgreSQL)
# =========================
spring.datasource.url=jdbc:postgresql://localhost:5432/cloud-store-db
spring.datasource.username=postgres
spring.datasource.password=hbwv6273
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.time_zone=UTC

# =========================
# Flyway Miscellaneous Configs
# =========================
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.probes.enabled=true

# =========================
# Resilience4j Configs
# =========================
resilience4j.circuitbreaker.instances.users.sliding-window-type=COUNT_BASED
resilience4j.circuitbreaker.instances.users.sliding-window-size=20
resilience4j.circuitbreaker.instances.users.failure-rate-threshold=50
resilience4j.circuitbreaker.instances.users.wait-duration-in-open-state=50s

# =========================
# JWT & Security Settings
# =========================
app.jwt.issuer=samson-backend

jwt.rotating-keys.issuer=samson-backend
jwt.rotating-keys.current-key-id=k1
jwt.rotating-keys.access-ttl=15m
jwt.rotating-keys.refresh-ttl=7d

jwt.rotating-keys.key-specification-list[0].key-id=k1
jwt.rotating-keys.key-specification-list[0].secret-base64=YXNnONz14eXKalXe5uis3ks178yxgL592ZbmYKnlZ4s

# Optional: add a second key (for rotation)
jwt.rotating-keys.key-specification-list[1].key-id=k2
jwt.rotating-keys.key-specification-list[1].secret-base64=nMwPAY0c8iMjnbJMrfokaOd5aXn_cCFzEZUNVg0cLM0

app.storage.bucket=cloudstore-user-files
app.storage.max-file-size-bytes=10737418240

app.minio.endpoint=http://192.168.50.17:9000

# Todo: change this
app.minio.access-key=cloudadmin
app.minio.secret-key=hbwv6273


# =========================
# Hibernate / Enum settings
# =========================
# Prevent Hibernate from using PostgreSQL native ENUM types (which can cause column drops on schema update)
spring.jpa.properties.hibernate.type.preferred_enum_jdbc_type=VARCHAR

# =========================
# Spring Session
# =========================
# We use stateless JWT auth; disable session persistence
spring.session.store-type=none

# =========================
# CORS (Next.js dev)
# =========================
app.cors.allowed-origins=http://localhost:3000

# =========================
# Storage behavior
# =========================
# How long items stay in trash before being purged by the cleanup job
app.storage.trash-retention-days=30
# TTL for pre-signed upload/download URLs
app.storage.presign-ttl=15m
# Multipart uploads
app.storage.multipart.part-size-bytes=8388608
```

## src/main/resources/db/migration/V1__init_storage.sql

```sql
-- Core schema for CloudStore

-- Useful for gen_random_uuid() if you decide to default UUIDs at DB level.
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =========================
-- Users & Auth
-- =========================

CREATE TABLE IF NOT EXISTS users (
    user_id         UUID PRIMARY KEY,
    email           TEXT NOT NULL UNIQUE,
    username        TEXT NOT NULL UNIQUE,
    hashed_password TEXT NOT NULL,
    role            VARCHAR(16) NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL,
    version         BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS refresh_tokens (
    token_id   UUID PRIMARY KEY,
    user_id    UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    jwt_id     TEXT NOT NULL UNIQUE,
    expires_at TIMESTAMPTZ NOT NULL,
    revoked    BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS idempotency_keys (
    id              UUID PRIMARY KEY,
    idempotency_key TEXT NOT NULL UNIQUE,
    request_hash    TEXT NOT NULL,
    response_code   INTEGER,
    response_body   TEXT,
    status          VARCHAR(20) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_idem_created_at ON idempotency_keys(created_at);

-- =========================
-- Storage nodes
-- =========================

CREATE TABLE IF NOT EXISTS storage_nodes (
    id              UUID PRIMARY KEY,
    owner_id        UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    parent_id       UUID REFERENCES storage_nodes(id) ON DELETE SET NULL,
    name            TEXT NOT NULL,
    path            TEXT NOT NULL,
    type            VARCHAR(20) NOT NULL,

    object_key      TEXT,
    size_bytes      BIGINT,
    mime_type       TEXT,
    checksum_sha256 VARCHAR(64),

    is_trashed      BOOLEAN NOT NULL DEFAULT FALSE,
    trashed_at      TIMESTAMPTZ,

    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL,
    version         BIGINT NOT NULL DEFAULT 0
);

-- Speed up folder browsing
CREATE INDEX IF NOT EXISTS idx_nodes_owner_parent_active ON storage_nodes(owner_id, parent_id) WHERE is_trashed = FALSE;
CREATE INDEX IF NOT EXISTS idx_nodes_owner_parent_trashed ON storage_nodes(owner_id, parent_id) WHERE is_trashed = TRUE;

-- Trash cleanup
CREATE INDEX IF NOT EXISTS idx_nodes_trashed_at ON storage_nodes(trashed_at) WHERE is_trashed = TRUE;

-- Name uniqueness within a folder for non-trashed nodes (case-insensitive)
-- Use a fixed UUID for NULL parent_id so root-level names are also unique.
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_indexes WHERE schemaname = 'public' AND indexname = 'ux_nodes_owner_parent_name_active'
    ) THEN
        EXECUTE $$
            CREATE UNIQUE INDEX ux_nodes_owner_parent_name_active
            ON storage_nodes(owner_id, COALESCE(parent_id, '00000000-0000-0000-0000-000000000000'::uuid), LOWER(name))
            WHERE is_trashed = FALSE;
        $$;
    END IF;
END $$;

-- =========================
-- File versions
-- =========================

CREATE TABLE IF NOT EXISTS file_versions (
    id              UUID PRIMARY KEY,
    node_id         UUID NOT NULL REFERENCES storage_nodes(id) ON DELETE CASCADE,
    version_no      INTEGER NOT NULL,
    object_key      TEXT NOT NULL,
    size_bytes      BIGINT NOT NULL,
    mime_type       TEXT,
    checksum_sha256 VARCHAR(64),
    created_at      TIMESTAMPTZ NOT NULL,
    CONSTRAINT ux_file_versions_node_version UNIQUE(node_id, version_no)
);

-- =========================
-- Share links
-- =========================

CREATE TABLE IF NOT EXISTS share_links (
    id             UUID PRIMARY KEY,
    node_id        UUID NOT NULL REFERENCES storage_nodes(id) ON DELETE CASCADE,
    token          TEXT NOT NULL UNIQUE,
    created_at     TIMESTAMP NOT NULL,
    expires_at     TIMESTAMP,
    is_active      BOOLEAN NOT NULL DEFAULT TRUE,
    max_downloads  INTEGER,
    download_count INTEGER NOT NULL DEFAULT 0,
    password_hash  TEXT
);

CREATE INDEX IF NOT EXISTS idx_share_node ON share_links(node_id);

-- =========================
-- Audit events
-- =========================

CREATE TABLE IF NOT EXISTS audit_events (
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    action     VARCHAR(64) NOT NULL,
    node_id    UUID,
    metadata   JSONB,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_audit_user_time ON audit_events(user_id, created_at DESC);

```

## src/main/resources/db/migration/V2__spring_session_postgresql.sql

```sql
CREATE TABLE IF NOT EXISTS SPRING_SESSION (
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX IF NOT EXISTS SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX IF NOT EXISTS SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX IF NOT EXISTS SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE IF NOT EXISTS SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BYTEA NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID)
    REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);
```

## src/test/java/com/samson/cloudstore/MinIOIntegrationTest.java

```java
package com.samson.cloudstore;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.samson.cloudstore.config.MinIOProperties;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        // "app.minio.enabled=true",
        "app.minio.endpoint=http://192.168.50.17:9000",
        "app.minio.access-key=cloudadmin",
        "app.minio.secret-key=hbwv6273"
})
class MinIOIntegrationTest {

    @Autowired
    private MinioClient minioClient;

    @Test
    void shouldAuthenticateWithMinio() {
        assertDoesNotThrow(() -> {
            List<Bucket> buckets = minioClient.listBuckets();

            for (Bucket bucket : buckets) System.out.println(bucket.name());
        });
    }
}
```

## src/test/java/com/samson/cloudstore/MinioCredentialVerificationTest.java

```java
package com.samson.cloudstore;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class MinioCredentialVerificationTest {

    @Autowired
    private MinioClient minioClient;

    @Test
    void credentialsShouldWorkAgainstMinio() {
        assertDoesNotThrow(() -> minioClient.listBuckets());
    }
}
```

