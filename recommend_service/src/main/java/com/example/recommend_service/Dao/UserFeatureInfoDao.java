package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.UserFeatureInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFeatureInfoDao extends JpaRepository<UserFeatureInfoEntity, Integer> {
    List<UserFeatureInfoEntity> findByRenew(boolean renew);
    UserFeatureInfoEntity findById(int userId);
}
