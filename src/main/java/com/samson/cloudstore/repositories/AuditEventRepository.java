package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {
    List<AuditEvent> findTop50ByUserIdOrderByCreatedAtDesc(UUID userId);
}
