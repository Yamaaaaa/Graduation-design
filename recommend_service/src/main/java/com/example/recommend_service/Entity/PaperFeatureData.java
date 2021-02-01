package com.example.recommend_service.Entity;

import java.util.Map;

public class PaperFeatureData {
    int topicId;
    String topicName;
    Double topicDegree;
    Map<Integer, Double> topicTag;

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Double getTopicDegree() {
        return topicDegree;
    }

    public void setTopicDegree(Double topicDegree) {
        this.topicDegree = topicDegree;
    }

    public Map<Integer, Double> getTopicTag() {
        return topicTag;
    }

    public void setTopicTag(Map<Integer, Double> topicTag) {
        this.topicTag = topicTag;
    }

    public PaperFeatureData(int topicId, String topicName, Double topicDegree, Map<Integer, Double> topicTag) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.topicDegree = topicDegree;
        this.topicTag = topicTag;
    }

    public PaperFeatureData() {
    }
}
