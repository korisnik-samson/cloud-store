package com.samson.cloudstore.config;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
@EnableConfigurationProperties({ MinIOProperties.class, StorageProperties.class })
@ConditionalOnProperty(prefix="app.minio", name="enabled", havingValue="true", matchIfMissing=true)
public class MinIOConfig {

    @Bean
    public MinioClient minioClient(@NonNull MinIOProperties properties) {
        return MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

}
