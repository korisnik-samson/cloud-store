package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.FileMetaData;
import com.samson.cloudstore.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, UUID> {

    @Query("SELECT f FROM FileMetaData f WHERE f.owner = :owner AND f.parentFolder IS NULL AND f.isTrashed = false ORDER BY f.isFolder DESC, f.fileName ASC")
    List<FileMetaData> listRoot(@Param("owner") Users owner);

    @Query("SELECT f FROM FileMetaData f WHERE f.owner = :owner AND f.parentFolder.id = :parentId AND f.isTrashed = false ORDER BY f.isFolder DESC, f.fileName ASC")
    List<FileMetaData> listByParent(@Param("owner") Users owner, @Param("parentId") UUID parentId);

    @Query("SELECT f FROM FileMetaData f WHERE f.id = :id AND f.owner = :owner AND f.isTrashed = false")
    Optional<FileMetaData> findOwnedActive(@Param("id") UUID id, @Param("owner") Users owner);

    @Query("SELECT f FROM FileMetaData f WHERE f.owner = :owner AND f.isTrashed = false AND LOWER(f.fileName) LIKE LOWER(CONCAT('%', :q, '%')) ORDER BY f.updated_at DESC")
    List<FileMetaData> searchByName(@Param("owner") Users owner, @Param("q") String q);

    @Query("SELECT COUNT(f) > 0 FROM FileMetaData f WHERE f.owner = :owner AND f.isTrashed = false AND LOWER(f.fileName) = LOWER(:name) AND ((:parentId IS NULL AND f.parentFolder IS NULL) OR (f.parentFolder.id = :parentId))")
    boolean nameExistsInFolder(@Param("owner") Users owner, @Param("parentId") UUID parentId, @Param("name") String name);

    @Query("SELECT f FROM FileMetaData f WHERE f.id = :id AND f.owner = :owner")
    Optional<FileMetaData> findOwnedAny(@Param("id") UUID id, @Param("owner") Users owner);
}
