package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StorageNodeRepository extends JpaRepository<StorageNode, UUID> {
    List<StorageNode> findByOwnerAndParentIdAndTrashedFalseOrderByTypeAscNameAsc(Users owner, UUID parentId);

    List<StorageNode> findByOwnerAndParentIsNullAndTrashedFalseOrderByTypeAscNameAsc(Users owner);

    Optional<StorageNode> findByIdAndOwner(UUID id, Users owner);

    boolean existsByOwnerAndParentIdAndNameIgnoreCaseAndTrashedFalse(Users owner, UUID parentId, String name);

    boolean existsByOwnerAndParentIsNullAndNameIgnoreCaseAndTrashedFalse(Users owner, String name);

    List<StorageNode> findByOwnerAndNameContainingIgnoreCaseAndTrashedFalseOrderByUpdatedAtDesc(Users owner, String name);
}