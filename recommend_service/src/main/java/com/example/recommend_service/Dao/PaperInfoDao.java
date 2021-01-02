package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.PaperInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperInfoDao extends JpaRepository<PaperInfoEntity, Integer> {

}
