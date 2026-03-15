package com.samson.cloudstore.services;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ObjectStorageService {

    private final MinioClient minioClient;

    @Value("${app.storage.bucket:cloudstore-user-files}")
    private String bucket;

    public String generatePresignedUploadUrl(String objectKey, int expiryMinutes) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucket)
                        .object(objectKey)
                        .method(Method.PUT)
                        .expiry(expiryMinutes, TimeUnit.MINUTES)
                        .build()
        );
    }

    public String generatePresignedDownloadUrl(String objectKey, int expiryMinutes) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucket)
                        .object(objectKey)
                        .method(Method.GET)
                        .expiry(expiryMinutes, TimeUnit.MINUTES)
                        .build()
        );
    }

    public StatObjectResponse stat(String objectKey) throws Exception {
        return minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectKey)
                        .build()
        );
    }

    public void remove(String objectKey) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectKey)
                        .build()
        );
    }

    public String bucket() {
        return bucket;
    }
}
