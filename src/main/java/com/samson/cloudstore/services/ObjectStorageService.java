package com.samson.cloudstore.services;

import com.samson.cloudstore.config.StorageProperties;
import io.minio.*;
import io.minio.http.Method;

// TODO: verify class source - (io.minio.messages.ComposeSource)
import io.minio.ComposeSource;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ObjectStorageService {

    private final MinioClient minioClient;
    private final StorageProperties storageProperties;

    public void ensureBucketExists() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(storageProperties.getBucket()).build()
            );

            if (!exists) minioClient.makeBucket(MakeBucketArgs.builder().bucket(storageProperties.getBucket()).build());

        } catch (Exception e) {
            throw new RuntimeException("Failed to ensure bucket exists", e);
        }
    }

    public void uploadObject(String objectKey, InputStream inputStream, long size, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .stream(inputStream, size, -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Upload to object storage failed", e);
        }
    }

    public String createPresignedPutUrl(String objectKey, String contentType) {
        try {
            int expiresSeconds = ttlSeconds(storageProperties.getPresignTtl());
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .expiry(expiresSeconds)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create presigned PUT URL", e);
        }
    }

    public String createPresignedGetUrl(String objectKey) {
        try {
            int expiresSeconds = ttlSeconds(storageProperties.getPresignTtl());
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .expiry(expiresSeconds)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create presigned GET URL", e);
        }
    }

    public void deleteObject(String objectKey) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete object", e);
        }
    }

    /**
     * Compose (server-side concatenate) parts into a single object.
     * Requires that each part was uploaded as an object in the same bucket.
     */
    public void composeObject(String targetKey, List<String> partKeys) {
        try {
            List<ComposeSource> sources = partKeys.stream()
                    .map(k -> ComposeSource.builder().bucket(storageProperties.getBucket()).object(k).build())
                    .toList();

            minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(storageProperties.getBucket())
                            .object(targetKey)
                            .sources(sources)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to compose object", e);
        }
    }

    private int ttlSeconds(Duration ttl) {
        if (ttl == null) return 900;
        long s = ttl.getSeconds();

        if (s < 1) return 1;
        if (s > 60L * 60 * 24 * 7) return (int) (60L * 60 * 24 * 7); // cap at 7 days

        return (int) s;
    }

    public String generatePresignedUploadUrl(String objectKey, int expiryMinutes) {
        Duration ttl = Duration.ofMinutes(Math.max(1, expiryMinutes));
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .expiry(ttlSeconds(ttl))
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create presigned upload URL", e);
        }
    }

    public String generatePresignedDownloadUrl(String objectKey, int expiryMinutes) {
        Duration ttl = Duration.ofMinutes(Math.max(1, expiryMinutes));
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .expiry(ttlSeconds(ttl))
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create presigned download URL", e);
        }
    }

    public io.minio.StatObjectResponse stat(String objectKey) {
        try {
            return minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(storageProperties.getBucket())
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to stat object", e);
        }
    }
}