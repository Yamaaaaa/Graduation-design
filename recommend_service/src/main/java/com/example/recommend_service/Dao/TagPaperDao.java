package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.PaperInfoEntity;
import com.example.recommend_service.Entity.TagPaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagPaperDao extends JpaRepository<TagPaperEntity, Integer> {

}
