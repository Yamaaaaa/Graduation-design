package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.PaperFeatureEntity;
import com.example.recommend_service.Entity.PaperFeatureEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaperFeatureDao extends JpaRepository<PaperFeatureEntity, PaperFeatureEntityPK> {
    List<PaperFeatureEntity> findByPaperId(int paperId);
    List<PaperFeatureEntity> findByPaperIdAndDegreeGreaterThanEqual(int paperId, float degree);
    List<PaperFeatureEntity> findByTopicIdAndDegreeGreaterThan(int topicId, float degree);
    @Query(value = "select distinct paper_id from paper_feature ", nativeQuery = true)
    List<Integer> findAllPaperId();
    boolean existsByPaperIdAndTopicId(int paperId, int topicId);
    PaperFeatureEntity findByPaperIdAndTopicId(int paperId, int topicId);
    List<PaperFeatureEntity> findTop3ByPaperIdOrderByDegreeDesc(int paperId);
    List<PaperFeatureEntity> findAllByPaperId(int paperId);
}
