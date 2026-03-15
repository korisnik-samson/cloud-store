package com.samson.cloudstore.services;

import com.samson.cloudstore.models.FileMetaData;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.FileMetaDataRepository;
import com.samson.cloudstore.repositories.UserRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
public class StorageService {

    private final MinioClient minioClient;
    private final FileMetaDataRepository fileMetaDataRepository;
    private final UserRepository userRepository;

    @Autowired
    public StorageService(MinioClient minioClient, FileMetaDataRepository fileMetaDataRepository, UserRepository userRepository) {
        this.minioClient = minioClient;
        this.fileMetaDataRepository = fileMetaDataRepository;
        this.userRepository = userRepository;
    }

    public void uploadFile(@NonNull MultipartFile file, UUID parentFolderId) throws Exception {
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        Users user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        String objectName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        // Upload file to MinIO
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket("cloudstore-user-files")
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        // Save the metadata
        FileMetaData fileMetaData = new FileMetaData();

        fileMetaData.setFileName(file.getOriginalFilename());
        fileMetaData.setS3ObjectName(objectName);
        fileMetaData.setSize(file.getSize());
        fileMetaData.setOwner(user);
        fileMetaData.setContentType(file.getContentType() != null ? file.getContentType() : "application/octet-stream");
        fileMetaData.setFileName(file.getOriginalFilename());
        fileMetaData.setFolder(false); // if field naming is boolean accessor-compatible

        // TODO: Set parent folder logic here

        fileMetaDataRepository.save(fileMetaData);
    }

}
