package com.samson.cloudstore.models;

import com.samson.cloudstore.utilities.NodeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NodeType type; // FILE or FOLDER

    @Column(name = "mime_type", length = 255)
    private String mimeType;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "object_key", length = 512)
    private String objectKey; // only for FILE

    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private StorageNode parent;

    @Column(nullable = false, length = 1024)
    private String path; // e.g. /docs/reports

    @Column(name = "is_trashed", nullable = false)
    private boolean trashed = false;

    @Column(name = "trashed_at")
    private OffsetDateTime trashedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Users owner;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}