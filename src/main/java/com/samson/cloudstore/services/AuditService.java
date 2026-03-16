package com.samson.cloudstore.services;

import com.samson.cloudstore.models.AuditEvent;
import com.samson.cloudstore.repositories.AuditEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditEventRepository auditEventRepository;

    public void log(UUID userId, String action, UUID nodeId, String metadataJson) {
        AuditEvent auditEvent = AuditEvent.builder()
                .userId(userId)
                .action(action)
                .nodeId(nodeId)
                .metadata(metadataJson)
                .createdAt(OffsetDateTime.now())
                .build();

        auditEventRepository.save(auditEvent);
    }

    public List<AuditEvent> latestForUser(UUID userId) {
        return auditEventRepository.findTop50ByUserIdOrderByCreatedAtDesc(userId);
    }

}
