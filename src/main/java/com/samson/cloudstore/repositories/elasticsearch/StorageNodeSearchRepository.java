package com.samson.cloudstore.repositories.elasticsearch;

import com.samson.cloudstore.models.elasticsearch.StorageNodeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StorageNodeSearchRepository extends ElasticsearchRepository<StorageNodeDocument, String> {

    List<StorageNodeDocument> findByNameAndOwnerIdAndTrashedFalse(String name, UUID ownerId);

    // More complex queries can be added here or via ElasticsearchRestTemplate
}
