package com.samson.cloudstore.services;

import com.samson.cloudstore.config.StorageProperties;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.repositories.IdempotencyRepository;
import com.samson.cloudstore.repositories.RefreshTokenRepository;
import com.samson.cloudstore.repositories.StorageNodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanupService {

    private final StorageProperties storageProperties;
    private final StorageNodeRepository storageNodeRepository;
    private final StorageNodeService storageNodeService;
    private final ShareService shareService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final IdempotencyRepository idempotencyRepository;

    // Every day at 03:30
    @Scheduled(cron = "0 30 3 * * *")
    public void dailyCleanup() {
        purgeExpiredShares();
        purgeExpiredRefreshTokens();
        purgeOldIdempotencyKeys();
        purgeOldTrash();
    }

    // Also run every 30 minutes for faster feedback in dev
    @Scheduled(fixedDelay = 30 * 60 * 1000L)
    public void frequentDevCleanup() {
        purgeExpiredRefreshTokens();
        purgeOldIdempotencyKeys();
    }

    private void purgeExpiredShares() {
        try {
            int count = shareService.purgeExpired();
            if (count > 0) log.info("Deactivated {} expired share links", count);

        } catch (Exception e) {
            log.warn("Share cleanup failed", e);
        }
    }

    private void purgeExpiredRefreshTokens() {
        try {
            long deleted = refreshTokenRepository.deleteByExpiresAtBefore(OffsetDateTime.now());
            if (deleted > 0) log.info("Deleted {} expired refresh tokens", deleted);

        } catch (Exception e) {
            log.warn("Refresh token cleanup failed", e);
        }
    }

    private void purgeOldIdempotencyKeys() {
        try {
            // keep idempotency keys 7 days
            OffsetDateTime cutoff = OffsetDateTime.now().minus(7, ChronoUnit.DAYS);

            long deleted = idempotencyRepository.deleteByExpiresAtBefore(cutoff);
            if (deleted > 0) log.info("Deleted {} old idempotency keys", deleted);

        } catch (Exception e) {
            log.warn("Idempotency cleanup failed", e);
        }
    }

    private void purgeOldTrash() {
        try {
            int retentionDays = storageProperties.getTrashRetentionDays() == null ? 30 : storageProperties.getTrashRetentionDays();
            OffsetDateTime cutoff = OffsetDateTime.now().minus(retentionDays, ChronoUnit.DAYS);

            List<StorageNode> roots = storageNodeRepository.findAllTrashRootsOlderThan(cutoff);
            if (roots.isEmpty()) return;

            int purged = 0;

            for (StorageNode r : roots) {
                try {
                    storageNodeService.purge(r.getOwnerId(), r.getId());
                    purged++;

                } catch (Exception e) {
                    log.warn("Failed to purge trashed node {}", r.getId(), e);
                }
            }

            if (purged > 0) log.info("Purged {} trash roots older than {} days", purged, retentionDays);

        } catch (Exception e) {
            log.warn("Trash purge failed", e);
        }
    }

}