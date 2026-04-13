package com.samson.cloudstore.services;

import com.samson.cloudstore.config.RabbitMQConfig;
import com.samson.cloudstore.dto.FileUploadedMessage;
import com.samson.cloudstore.dto.NodeDtos;
import com.samson.cloudstore.dto.UploadDtos;
import com.samson.cloudstore.models.FileMetaData;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.FileMetaDataRepository;
import io.minio.StatObjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final ObjectStorageService objectStorage;
    private final FileMetaDataRepository fileRepo;
    private final NodeService nodeService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.storage.max-file-size-bytes:10737418240}") // 10GB
    private long maxFileSizeBytes;

    @Transactional(readOnly = true)
    public UploadDtos.InitiateUploadResponse initiate(Users user, UploadDtos.InitiateUploadRequest req) throws Exception {
        if (req.sizeBytes() > maxFileSizeBytes) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File too large");
        }

        // validate parent folder (if any)
        nodeService.requireParentFolder(user, req.parentId());

        String safeName = req.fileName().trim();
        String objectKey = "users/" + user.getUserId() + "/" + UUID.randomUUID() + "/" + safeName;

        String url = objectStorage.generatePresignedUploadUrl(objectKey, 15);

        return new UploadDtos.InitiateUploadResponse(objectKey, url, 15 * 60);
    }

    @Transactional
    public NodeDtos.NodeResponse complete(Users user, UploadDtos.CompleteUploadRequest req) throws Exception {
        // security: ensure objectKey belongs to user
        String prefix = "users/" + user.getUserId() + "/";
        if (!req.objectKey().startsWith(prefix)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid object key");
        }

        FileMetaData parent = nodeService.requireParentFolder(user, req.parentId());

        StatObjectResponse stat = objectStorage.stat(req.objectKey());
        long actualSize = stat.size();
        if (actualSize != req.sizeBytes()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uploaded size mismatch");
        }

        FileMetaData node = FileMetaData.builder()
                .fileName(req.fileName().trim())
                .contentType(req.mimeType())
                .size(req.sizeBytes())
                .s3ObjectName(req.objectKey())
                .parentFolder(parent)
                .isFolder(false)
                .owner(user)
                .isTrashed(false)
                .build();

        FileMetaData saved = fileRepo.save(node);

        // Publish event
        publishUploadEvent(saved);

        return NodeService.toDto(saved);
    }

    private void publishUploadEvent(FileMetaData node) {
        FileUploadedMessage message = new FileUploadedMessage(
                node.getId(),
                node.getOwner().getUserId(),
                node.getFileName(),
                node.getContentType(),
                node.getS3ObjectName(),
                node.getSize()
        );

        String routingKey = node.getContentType() != null && node.getContentType().startsWith("image/")
                ? "file.uploaded.image"
                : "file.uploaded.other";

        rabbitTemplate.convertAndSend(RabbitMQConfig.CLOUDSTORE_EXCHANGE, routingKey, message);
    }
}
