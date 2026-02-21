package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdempotencyRepository extends JpaRepository<IdempotencyKey, String> {
    Long deleteByExpiresAtBefore(java.time.OffsetDateTime cutoff);
}
