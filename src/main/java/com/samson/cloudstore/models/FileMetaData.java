package com.samson.cloudstore.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    private Long size;

    private String s3ObjectName;

    @ManyToOne
    private FileMetaData parentFolder;

    @Column(name = "is_folder")
    private boolean isFolder;

    @Column(name = "is_trashed", nullable = false)
    private boolean isTrashed = false;

    @Column(name = "trashed_at")
    private OffsetDateTime trashed_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Users owner;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private OffsetDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private OffsetDateTime updated_at;
}
