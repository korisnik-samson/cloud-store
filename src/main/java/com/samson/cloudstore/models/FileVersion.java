package com.samson.cloudstore.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "file_versions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FileVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private StorageNode node;

    @Column(name = "version_no", nullable = false)
    private Integer versionNo;

    @Column(name = "object_key", nullable = false, length = 512)
    private String objectKey;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}