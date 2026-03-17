package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.ShareLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShareLinkRepository extends JpaRepository<ShareLink, UUID> {
    Optional<ShareLink> findByTokenAndActiveTrue(String token);

    Optional<ShareLink> findByToken(String token);

    @Query("select s from ShareLink s where s.expiresAt is not null and s.expiresAt < :now")
    List<ShareLink> findExpired(OffsetDateTime now);

    List<ShareLink> findAllByNodeOwnerIdOrderByCreatedAtDesc(UUID ownerId);
}