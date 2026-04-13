package com.samson.cloudstore.services;

import com.samson.cloudstore.dto.StorageUsageResponse;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.StorageNodeRepository;
import com.samson.cloudstore.config.StorageProperties;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StorageUsageService {

    private final StorageNodeRepository storageNodeRepository;
    private final StorageProperties storageProperties;

    @Autowired
    public StorageUsageService(StorageNodeRepository storageNodeRepository, StorageProperties storageProperties) {
        this.storageNodeRepository = storageNodeRepository;
        this.storageProperties = storageProperties;
    }

    public StorageUsageResponse getUsageForUser(@NonNull Users user) {
        long quotaBytes = storageProperties.getUserQuotaBytes();
        long usedBytes = storageNodeRepository.sumTotalUsage(user.getUserId());

        Map<String, Long> byCategory = new HashMap<>();

        // defaults
        byCategory.put("images", 0L);
        byCategory.put("videos", 0L);
        byCategory.put("documents", 0L);
        byCategory.put("audio", 0L);
        byCategory.put("archives", 0L);
        byCategory.put("other", 0L);

        List<Object[]> rows = storageNodeRepository.sumUsageByCategory(user.getUserId());

        for (Object[] row : rows) {
            String category = (String) row[0];
            Number bytes = (Number) row[1];

            byCategory.put(category, bytes.longValue());
        }

        return new StorageUsageResponse(usedBytes, quotaBytes, byCategory);
    }
}