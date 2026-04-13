package com.samson.cloudstore.services;

import com.samson.cloudstore.config.RabbitMQConfig;
import com.samson.cloudstore.dto.AuditEventMessage;
import com.samson.cloudstore.models.AuditEvent;
import com.samson.cloudstore.repositories.AuditEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditEventRepository auditEventRepository;
    private final RabbitTemplate rabbitTemplate;

    public void log(UUID userId, String action, UUID nodeId, String metadataJson) {
        AuditEventMessage message = new AuditEventMessage(
                userId,
                action,
                nodeId,
                metadataJson,
                OffsetDateTime.now()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CLOUDSTORE_EXCHANGE,
                RabbitMQConfig.AUDIT_ROUTING_KEY,
                message
        );
    }

    public List<AuditEvent> latestForUser(UUID userId) {
        return auditEventRepository.findTop50ByUserIdOrderByCreatedAtDesc(userId);
    }

}
