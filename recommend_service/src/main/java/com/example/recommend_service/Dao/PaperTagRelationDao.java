package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.PaperFeatureEntity;
import com.example.recommend_service.Entity.PaperFeatureEntityPK;
import com.example.recommend_service.Entity.PaperTagRelationEntity;
import com.example.recommend_service.Entity.PaperTagRelationEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaperTagRelationDao extends JpaRepository<PaperTagRelationEntity, PaperTagRelationEntityPK> {
    List<PaperTagRelationEntity> findTop6ByPaperIdOrderByDegreeDesc(int paperId);
    void deleteByTagId(int tag_id);
    List<PaperTagRelationEntity> findByTagId(int tag_id);
    PaperTagRelationEntity findByPaperIdAndTagId(int paper_id, int tag_id);
}
