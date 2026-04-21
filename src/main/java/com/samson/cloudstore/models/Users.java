package com.samson.cloudstore.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.samson.cloudstore.utilities.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_users_username", columnNames = "username")
    },
    indexes = {
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_username", columnList = "username")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ToString.Include
    @Column(name="user_id", updatable=false, nullable=false, unique=true)
    private UUID userId;

    @Column(name="username", nullable=false, unique=true)
    @ToString.Include
    private String username;

    @Column(name="email", nullable=false, unique=true)
    @ToString.Include
    private String email;

    @Column(name="hashed_password", nullable=false, length=72)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private String password;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", nullable=false)
    private OffsetDateTime updatedAt;

    @Version
    @Column(name="version", nullable=false)
    private Integer version;

    @Column(name="role", nullable=false)
    @Enumerated(EnumType.STRING)
    // @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private UserRole userRole;
}