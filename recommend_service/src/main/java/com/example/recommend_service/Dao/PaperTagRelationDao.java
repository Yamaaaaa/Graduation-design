package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.PaperFeatureEntity;
import com.example.recommend_service.Entity.PaperFeatureEntityPK;
import com.example.recommend_service.Entity.PaperTagRelationEntity;
import com.example.recommend_service.Entity.PaperTagRelationEntityPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaperTagRelationDao extends JpaRepository<PaperTagRelationEntity, PaperTagRelationEntityPK> {
    List<PaperTagRelationEntity> findTop6ByPaperIdOrderByDegreeDesc(int paperId);
    void deleteByTagId(int tag_id);
    List<PaperTagRelationEntity> findByTagId(int tag_id);
    List<PaperTagRelationEntity> findByPaperId(int paper_id);
    PaperTagRelationEntity findByPaperIdAndTagId(int paper_id, int tag_id);
    boolean existsByPaperIdAndTagId(int paper_id, int tag_id);
    @Query(value = "select distinct tag_id from paper_tag_relation ", nativeQuery = true)
    List<Integer> findAllTagId();
    @Query(value = "select distinct paper_id from paper_tag_relation ", nativeQuery = true)
    List<Integer> findAllPaperId();
    @Query(value = "select paper_id from paper_tag_relation where tagId=?1 and degree>?2 order by degree desc", nativeQuery = true)
    List<Integer> getPaperIdByTagIdAndDegree(int tagId, float degree, Pageable pageable);
    List<PaperTagRelationEntity> findByPaperIdAndDegreeGreaterThanEqual(int paperId, double degree);
}
