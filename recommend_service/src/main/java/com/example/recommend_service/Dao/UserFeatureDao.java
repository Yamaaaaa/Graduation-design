package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.UserFeatureEntity;
import com.example.recommend_service.Entity.UserFeatureEntityPK;
import com.example.recommend_service.Entity.UserFeatureInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFeatureDao extends JpaRepository<UserFeatureEntity, UserFeatureEntityPK> {
    List<UserFeatureEntity> findByUserId(int user_id);
    UserFeatureEntity findByUserIdAndTopicId(int user_id, int topic_id);
    boolean existsByUserIdAndTopicId(int user_id, int topic_id);
}
