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