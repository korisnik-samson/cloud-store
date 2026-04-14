package com.samson.cloudstore.services;

import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.elasticsearch.StorageNodeDocument;
import com.samson.cloudstore.repositories.StorageNodeRepository;
import com.samson.cloudstore.repositories.elasticsearch.StorageNodeSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final StorageNodeRepository storageNodeRepository;
    private final StorageNodeSearchRepository searchRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public void rebuildSearchIndex() {
        log.info("Starting search index rebuild...");
        
        // Clear index
        searchRepository.deleteAll();
        
        // Fetch all nodes
        List<StorageNode> allNodes = storageNodeRepository.findAll();
        
        List<StorageNodeDocument> docs = allNodes.stream()
                .map(node -> StorageNodeDocument.builder()
                        .id(node.getId().toString())
                        .ownerId(node.getOwnerId())
                        .name(node.getName())
                        .type(node.getType().name())
                        .mimeType(node.getMimeType())
                        .trashed(node.isTrashed())
                        .build())
                .toList();
        
        searchRepository.saveAll(docs);
        
        log.info("Search index rebuild complete. Indexed {} nodes.", docs.size());
    }

}
