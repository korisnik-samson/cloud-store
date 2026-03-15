package com.samson.cloudstore;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class MinioCredentialVerificationTest {

    @Autowired
    private MinioClient minioClient;

    @Test
    void credentialsShouldWorkAgainstMinio() {
        assertDoesNotThrow(() -> minioClient.listBuckets());
    }
}