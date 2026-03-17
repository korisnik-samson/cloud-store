package com.samson.cloudstore.utilities;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class Hashing {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String SHA256(String input) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(digest);

        } catch (Exception e) {
            throw new IllegalStateException("Error computing SHA-256 hash", e);
        }
    }

    public String hashPassword(String rawPassword) {
        if (rawPassword == null) throw new IllegalArgumentException("Password must not be null");

        return this.encoder.encode(rawPassword);
    }

    /** Verify a raw password/secret against a BCrypt hash. */
    public boolean verifyPassword(String rawPassword, String bcryptHash) {
        if (rawPassword == null || bcryptHash == null) return false;

        return this.encoder.matches(rawPassword, bcryptHash);
    }
}
