package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.UserPaperSimilarityEntity;
import com.example.recommend_service.Entity.UserPaperSimilarityEntityPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPaperSimilarityDao extends JpaRepository<UserPaperSimilarityEntity, UserPaperSimilarityEntityPK> {
    List<UserPaperSimilarityEntity> findByUserIdAndRelateValueGreaterThanOrderByRelateValueDesc(int userId, float value, Pageable pageable);

}
