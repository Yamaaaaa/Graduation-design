package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.PaperFeatureEntity;
import com.example.recommend_service.Entity.PaperFeatureEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaperFeatureDao extends JpaRepository<PaperFeatureEntity, PaperFeatureEntityPK> {
    List<PaperFeatureEntity> findByPaperId(int paperId);
}
