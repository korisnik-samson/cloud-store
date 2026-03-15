package com.samson.cloudstore.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "share_links",
        indexes = { @Index(name = "idx_share_token", columnList = "token", unique = true) }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ShareLink {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private FileMetaData node;

    @Column(nullable = false, unique = true, length = 128)
    private String token;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    @Column(name = "max_downloads")
    private Integer maxDownloads;

    @Column(name = "download_count", nullable = false)
    private Integer downloadCount = 0;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private OffsetDateTime created_at;
}
