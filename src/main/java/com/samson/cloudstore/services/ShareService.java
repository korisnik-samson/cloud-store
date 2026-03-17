package com.samson.cloudstore.services;

import com.samson.cloudstore.models.ShareLink;
import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.repositories.ShareLinkRepository;
import com.samson.cloudstore.repositories.StorageNodeRepository;
import com.samson.cloudstore.utilities.Hashing;
import com.samson.cloudstore.utilities.NodeType;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShareService {

    private static final SecureRandom RNG = new SecureRandom();

    private final ShareLinkRepository shareLinkRepository;
    private final StorageNodeRepository storageNodeRepository;
    private final ObjectStorageService objectStorageService;
    private final Hashing hashing = new Hashing();

    public ShareLink createShare(UUID ownerId, UUID nodeId, OffsetDateTime expiresAt, Integer maxDownloads, String password) {

        StorageNode node = storageNodeRepository.findByIdAndOwnerId(nodeId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Node not found"));

        if (node.getType() != NodeType.FILE) throw new IllegalArgumentException("Only FILE nodes can be shared");

        if (node.isTrashed()) throw new IllegalArgumentException("Cannot share trashed nodes");

        String token = generateToken();
        String passwordHash = (password == null || password.isBlank()) ? null : hashing.hashPassword(password);

        ShareLink shareLink = ShareLink.builder()
                .node(node)
                .token(token)
                .createdAt(OffsetDateTime.now())
                .expiresAt(expiresAt)
                .active(true)
                .maxDownloads(maxDownloads)
                .downloadCount(0)
                .passwordHash(passwordHash)
                .build();

        return shareLinkRepository.save(shareLink);
    }

    public ShareLink requireValid(String token) {
        ShareLink link = shareLinkRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid share token"));

        if (!link.isActive()) throw new IllegalArgumentException("Share link inactive");

        if (link.getExpiresAt() != null && link.getExpiresAt().isBefore(OffsetDateTime.now()))
            throw new IllegalArgumentException("Share link expired");

        if (link.getMaxDownloads() != null && link.getDownloadCount() >= link.getMaxDownloads())
            throw new IllegalArgumentException("Share link download limit reached");

        return link;
    }

    public boolean isPasswordRequired(String token) {
        ShareLink link = shareLinkRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid share token"));

        return link.getPasswordHash() != null;
    }

    public void verifyPassword(@NonNull ShareLink link, String password) {
        if (link.getPasswordHash() == null) return;

        if (password == null || password.isBlank() || !hashing.verifyPassword(password, link.getPasswordHash()))
            throw new IllegalArgumentException("Invalid password");
    }

    @Transactional
    public String getDownloadUrl(String token, String password) {
        ShareLink link = requireValid(token);
        verifyPassword(link, password);

        StorageNode node = link.getNode();
        if (node.isTrashed()) throw new IllegalArgumentException("Shared file is no longer available");

        // increment before generating (best-effort)
        link.setDownloadCount(link.getDownloadCount() + 1);
        shareLinkRepository.save(link);

        return objectStorageService.createPresignedGetUrl(node.getObjectKey());
    }

    public List<ShareLink> listMyShares(UUID ownerId) {
        return shareLinkRepository.findAllByNodeOwnerIdOrderByCreatedAtDesc(ownerId);
    }

    @Transactional
    public void revoke(UUID ownerId, UUID shareId) {
        ShareLink link = shareLinkRepository.findById(shareId)
                .orElseThrow(() -> new IllegalArgumentException("Share not found"));

        if (!link.getNode().getOwnerId().equals(ownerId)) throw new IllegalArgumentException("Not allowed");
        link.setActive(false);

        shareLinkRepository.save(link);
    }

    @Transactional
    public int purgeExpired() {
        List<ShareLink> expired = shareLinkRepository.findExpired(OffsetDateTime.now());

        expired.forEach(l -> l.setActive(false));
        shareLinkRepository.saveAll(expired);

        return expired.size();
    }

    private String generateToken() {
        byte[] bytes = new byte[24];
        RNG.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}