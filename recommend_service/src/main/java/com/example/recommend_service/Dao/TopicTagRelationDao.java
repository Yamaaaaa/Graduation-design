package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.TopicTagRelationEntity;
import com.example.recommend_service.Entity.TopicTagRelationEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicTagRelationDao extends JpaRepository<TopicTagRelationEntity, TopicTagRelationEntityPK> {
    void deleteByTagId(int tag_id);
    List<TopicTagRelationEntity> findAll();
    List<TopicTagRelationEntity> findByTopicId(int topicId);
    boolean existsByTopicIdAndTagIdAndDegreeGreaterThanEqual(int topicId, int tagId, double degree);
}
