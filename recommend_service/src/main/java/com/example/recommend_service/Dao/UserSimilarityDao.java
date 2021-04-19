package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.UserSimilarityEntity;
import com.example.recommend_service.Entity.UserSimilarityEntityPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSimilarityDao extends JpaRepository<UserSimilarityEntity, UserSimilarityEntityPK> {
    List<UserSimilarityEntity> findByUserIdAndRelateValueGreaterThanOrderByRelateValueDesc(int userId, float value, Pageable pageable);
}
