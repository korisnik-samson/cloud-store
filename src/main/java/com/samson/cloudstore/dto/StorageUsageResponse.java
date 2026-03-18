package com.samson.cloudstore.dto;

import java.util.Map;

public record StorageUsageResponse(
        long usedBytes,
        long quotaBytes,
        Map<String, Long> byCategoryBytes
) {}