package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicDao extends JpaRepository<TopicEntity, Integer> {
    TopicEntity findById(int topicId);
    void deleteAll();
    List<TopicEntity> findAll();
}
