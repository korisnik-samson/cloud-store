package com.samson.cloudstore.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {
    private String bucket;

    private Long maxFileSizeBytes;

    private Duration presignTtl = Duration.ofMinutes(15);

    private Integer trashRetentionDays = 30;

    private Long multipartPartSizeBytes = 8L * 1024 * 1024;

    private Long userQuotaBytes = 107374182400L;
}