package com.example.tag_service.Dao;

import com.example.tag_service.Entity.TagPaperEntity;
import com.example.tag_service.Entity.TagPaperEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagPaperDao extends JpaRepository<TagPaperEntity, TagPaperEntityPK> {
    @Query(nativeQuery = true, value = "select distinct paper_id from tag_paper")
    List<Integer> getAllPaperId();
    @Query(nativeQuery = true, value = "select tag_name from tag_paper where paper_id=?1")
    List<String> findAllByPaperId(int paperId);
    void deleteByPaperIdAndTagName(int paperId, String tagName);
    List<TagPaperEntity> findAll();
}
