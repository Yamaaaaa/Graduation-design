package com.example.recommend_service.Entity;

import java.util.Map;

public class PaperFeatureData {
    int topicId;
    String topicName;
    Float topicDegree;
    Map<String, Float> topicTag;

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

    public Float getTopicDegree() {
        return topicDegree;
    }

    public void setTopicDegree(Float topicDegree) {
        this.topicDegree = topicDegree;
    }

    public Map<String, Float> getTopicTag() {
        return topicTag;
    }

    public void setTopicTag(Map<String, Float> topicTag) {
        this.topicTag = topicTag;
    }

    public PaperFeatureData(int topicId, String topicName, Float topicDegree, Map<String, Float> topicTag) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.topicDegree = topicDegree;
        this.topicTag = topicTag;
    }

    public PaperFeatureData() {
    }
}
