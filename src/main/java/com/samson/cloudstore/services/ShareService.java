package com.samson.cloudstore.services;

import com.samson.cloudstore.dto.ShareDtos;
import com.samson.cloudstore.models.FileMetaData;
import com.samson.cloudstore.models.ShareLink;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.repositories.FileMetaDataRepository;
import com.samson.cloudstore.repositories.ShareLinkRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareLinkRepository shareRepo;
    private final FileMetaDataRepository fileRepo;
    private final PasswordEncoder passwordEncoder;
    private final ObjectStorageService objectStorage;

    @Transactional
    public ShareDtos.CreateShareResponse createShare(Users user, ShareDtos.@NonNull CreateShareRequest req) {
        FileMetaData node = fileRepo.findOwnedActive(req.nodeId(), user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));

        if (node.isFolder())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Folders cannot be shared yet");

        String token = randomTokenUrlSafe();

        ShareLink link = ShareLink.builder()
                .node(node)
                .token(token)
                .passwordHash((req.password() == null || req.password().isBlank()) ? null : passwordEncoder.encode(req.password()))
                .expiresAt(req.expiresHours() == null ? null : OffsetDateTime.now().plusHours(req.expiresHours()))
                .maxDownloads(req.maxDownloads())
                .downloadCount(0)
                .active(true)
                .build();

        shareRepo.save(link);

        return new ShareDtos.CreateShareResponse(token, "/share/" + token);
    }

    @Transactional
    public ShareDtos.ResolveShareResponse resolve(String token, ShareDtos.ResolveShareRequest req) throws Exception {
        ShareLink link = shareRepo.findByTokenAndActiveTrue(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Share link not found"));

        if (link.getExpiresAt() != null && link.getExpiresAt().isBefore(OffsetDateTime.now()))
            throw new ResponseStatusException(HttpStatus.GONE, "Share link expired");


        if (link.getMaxDownloads() != null && link.getDownloadCount() >= link.getMaxDownloads())
            throw new ResponseStatusException(HttpStatus.GONE, "Download limit reached");


        if (link.getPasswordHash() != null) {
            String pw = req == null ? null : req.password();

            if (pw == null || !passwordEncoder.matches(pw, link.getPasswordHash()))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        FileMetaData node = link.getNode();
        if (node == null || node.getS3ObjectName() == null || node.getS3ObjectName().isBlank())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Shared item unavailable");


        String downloadUrl = objectStorage.generatePresignedDownloadUrl(node.getS3ObjectName(), 10);

        link.setDownloadCount(link.getDownloadCount() + 1);
        shareRepo.save(link);

        return new ShareDtos.ResolveShareResponse(
                node.getFileName(),
                node.isFolder() ? "FOLDER" : "FILE",
                node.getSize(),
                downloadUrl
        );
    }

    private String randomTokenUrlSafe() {
        byte[] b = new byte[24];
        new SecureRandom().nextBytes(b);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(b);
    }
}
