package com.samson.cloudstore.messaging;

import com.samson.cloudstore.config.RabbitMQConfig;
import com.samson.cloudstore.dto.FileUploadedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileUploadedConsumer {

    @RabbitListener(queues = RabbitMQConfig.THUMBNAIL_QUEUE)
    public void consumeFileUploadEvent(FileUploadedMessage message) {
        log.info("Received file upload event for thumbnail generation: {}", message.fileName());

        if (message.mimeType() != null && message.mimeType().startsWith("image/")) {
            log.info("Simulating thumbnail generation for image: {} (Size: {} bytes)", message.fileName(), message.sizeBytes());
            // TODO: Integrate with a thumbnail service or implement logic here
        } else {
            log.debug("Skipping thumbnail generation for non-image file: {}", message.fileName());
        }
    }
}
