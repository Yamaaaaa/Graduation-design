package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.PaperInfoEntity;
import com.example.recommend_service.Entity.TagPaperEntity;
import com.example.recommend_service.Entity.TagPaperEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagPaperDao extends JpaRepository<TagPaperEntity, TagPaperEntityPK> {
    @Query(nativeQuery = true, value = "select distinct paper_id from tag_paper")
    List<Integer> getAllPaperId();
    @Query(nativeQuery = true, value = "select tag_id from tag_paper where paper_id=?1")
    List<Integer> findAllByPaperId(int paperId);
}
