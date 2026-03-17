package com.samson.cloudstore.models;

import com.samson.cloudstore.utilities.NodeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "storage_nodes",
        indexes = {
                @Index(name = "idx_node_owner_parent", columnList = "owner_id,parent_id"),
                @Index(name = "idx_node_owner_name", columnList = "owner_id,name"),
                @Index(name = "idx_node_path", columnList = "path")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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