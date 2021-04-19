package com.example.tag_service.Dao;

import com.example.tag_service.Entity.SameTagEntity;
import com.example.tag_service.Entity.SameTagEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SameTagDao extends JpaRepository<SameTagEntity, SameTagEntityPK> {
    SameTagEntity findByName(String name);
    boolean existsByName(String name);
    @Query(value = "select name from same_tag where same_tag_name=?1", nativeQuery = true)
    List<String> findNameBySameTagName(String tagId);

    List<String> findDistinctByNameContains(String name);
}
