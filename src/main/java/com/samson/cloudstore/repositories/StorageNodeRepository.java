package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.StorageNode;
import com.samson.cloudstore.models.Users;
import com.samson.cloudstore.utilities.NodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StorageNodeRepository extends JpaRepository<StorageNode, UUID> {
    List<StorageNode> findByOwnerIdAndParent_IdAndTrashedFalseOrderByTypeAscNameAsc(UUID ownerId, UUID parentId);

    List<StorageNode> findByOwnerIdAndParentIsNullAndTrashedFalseOrderByTypeAscNameAsc(UUID ownerId);

    List<StorageNode> findByOwnerIdAndParent_IdAndTrashedTrueOrderByTypeAscNameAsc(UUID ownerId, UUID parentId);

    List<StorageNode> findByOwnerIdAndParentIsNullAndTrashedTrueOrderByTypeAscNameAsc(UUID ownerId);

    Optional<StorageNode> findByIdAndOwnerId(UUID id, UUID ownerId);

    boolean existsByOwnerIdAndParentIsNullAndNameIgnoreCaseAndTrashedFalse(UUID ownerId, String name);

    boolean existsByOwnerIdAndParent_IdAndNameIgnoreCaseAndTrashedFalse(UUID ownerId, UUID parentId, String name);

    @Query("select n from StorageNode n where n.ownerId = :ownerId and n.trashed = true and (n.parent is null or n.parent.trashed = false) order by n.updatedAt desc")
    List<StorageNode> findTrashRoots(@Param("ownerId") UUID ownerId);
    @Query("select n from StorageNode n where n.ownerId = :ownerId and n.trashed = true and n.trashedAt is not null and n.trashedAt < :cutoff and (n.parent is null or n.parent.trashed = false) order by n.trashedAt asc")
    List<StorageNode> findTrashRootsOlderThan(@Param("ownerId") UUID ownerId, @Param("cutoff") java.time.OffsetDateTime cutoff);

    @Query("select n from StorageNode n where n.ownerId = :ownerId and n.trashed = false and lower(n.name) like lower(concat('%', :query, '%')) order by n.type asc, n.name asc")
    List<StorageNode> search(@Param("ownerId") UUID ownerId, @Param("query") String query);

    @Query("select coalesce(sum(n.sizeBytes), 0) from StorageNode n where n.ownerId = :ownerId and n.trashed = false and n.type = :type")
    long sumSizeByOwnerAndTypeNotTrashed(@Param("ownerId") UUID ownerId, @Param("type") NodeType type);

    long countByOwnerIdAndTrashedTrue(UUID ownerId);

    long countByOwnerIdAndTrashedFalse(UUID ownerId);

    @Query("select n from StorageNode n where n.trashed = true and n.trashedAt is not null and n.trashedAt < :cutoff and (n.parent is null or n.parent.trashed = false) order by n.trashedAt asc")
    List<StorageNode> findAllTrashRootsOlderThan(@Param("cutoff") java.time.OffsetDateTime cutoff);
}