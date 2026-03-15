package com.samson.cloudstore;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.samson.cloudstore.config.MinIOProperties;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        // "app.minio.enabled=true",
        "app.minio.endpoint=http://192.168.50.17:9000",
        "app.minio.access-key=cloudadmin",
        "app.minio.secret-key=hbwv6273"
})
class MinIOIntegrationTest {

    @Autowired
    private MinioClient minioClient;

    @Test
    void shouldAuthenticateWithMinio() {
        assertDoesNotThrow(() -> {
            List<Bucket> buckets = minioClient.listBuckets();

            for (Bucket bucket : buckets) System.out.println(bucket.name());
        });
    }
}