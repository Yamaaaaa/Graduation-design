package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.PaperFeatureEntity;
import com.example.recommend_service.Entity.PaperFeatureEntityPK;
import com.example.recommend_service.Entity.PaperTagRelationEntity;
import com.example.recommend_service.Entity.PaperTagRelationEntityPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PaperTagRelationDao extends JpaRepository<PaperTagRelationEntity, PaperTagRelationEntityPK> {
    List<PaperTagRelationEntity> findTop6ByPaperIdOrderByDegreeDesc(int paperId);
    void deleteByTagName(String tagName);
    List<PaperTagRelationEntity> findByTagName(String tagName);
    PaperTagRelationEntity findByPaperIdAndTagName(int paper_id, String tagName);
    boolean existsByPaperIdAndTagName(int paper_id, String tagName);

    @Query(value = "select distinct tag_name from paper_tag_relation ", nativeQuery = true)
    List<String> findAllTagName();

    @Query(value = "select distinct paper_id from paper_tag_relation ", nativeQuery = true)
    List<Integer> findAllPaperId();

    List<PaperTagRelationEntity> findAllByTagNameAndDegreeGreaterThan(String tagName, float degree);

    @Query(value = "select tag_name from paper_tag_relation where paper_id=?1 and degree>?2", nativeQuery = true)
    Set<String> findTagNameByPaperIdAndDegreeGreaterThanEqual(int paperId, float degree);

    List<PaperTagRelationEntity> findByPaperIdAndDegreeGreaterThanEqual(int paperId, float degree);
    List<PaperTagRelationEntity> findAllByRenew(boolean renew);

    boolean existsByPaperIdAndTagNameAndDegreeGreaterThan(int paperId, String tagName, float degree);
}
