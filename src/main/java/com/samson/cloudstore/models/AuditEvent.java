package com.samson.cloudstore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;
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
    @Column(name="id", nullable = false, updatable = false)
    private UUID id;

    @Column(name="user_id", nullable = false)
    private UUID userId;

    @Column(name="action", nullable = false, length = 64)
    private String action;

    @Column(name="node_id")
    private UUID nodeId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="metadata", columnDefinition="jsonb")

    // Todo: could be -> private Map<String, Object> metadata;
    private String metadata;

    @Column(name="created_at", nullable = false)
    private OffsetDateTime createdAt;

}
