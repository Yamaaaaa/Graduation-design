package com.example.recommend_service.Entity;

import java.util.Map;

public class TopicTagData {
    int topicId;
    String topicName;
    Map<Integer, Double> tagRelation;

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setTagRelation(Map<Integer, Double> tagRelation) {
        this.tagRelation = tagRelation;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public Map<Integer, Double> getTagRelation() {
        return tagRelation;
    }

    public TopicTagData(int topicId, String topicName, Map<Integer, Double> tagRelation) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.tagRelation = tagRelation;
    }
}
