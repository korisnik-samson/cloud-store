package com.samson.cloudstore.repositories;

import com.samson.cloudstore.models.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FileVersionRepository extends JpaRepository<FileVersion, UUID> {

    @Query("select coalesce(max(v.versionNo), 0) from FileVersion v where v.node = :nodeId")
    int findMaxVersionNo(@Param("nodeId") UUID nodeId);

    List<FileVersion> findByNodeIdOrderByVersionNoDesc(UUID nodeId);
}