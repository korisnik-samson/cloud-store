package com.samson.cloudstore.messaging;

import com.samson.cloudstore.config.RabbitMQConfig;
import com.samson.cloudstore.dto.AuditEventMessage;
import com.samson.cloudstore.models.AuditEvent;
import com.samson.cloudstore.repositories.AuditEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditEventConsumer {

    private final AuditEventRepository auditEventRepository;

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    public void consumeAuditEvent(AuditEventMessage message) {
        log.debug("Consuming audit event: {} for user: {}", message.action(), message.userId());

        AuditEvent event = AuditEvent.builder()
                .userId(message.userId())
                .action(message.action())
                .nodeId(message.nodeId())
                .metadata(message.metadata())
                .createdAt(message.createdAt())
                .build();

        auditEventRepository.save(event);
    }
}
