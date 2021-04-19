package com.example.recommend_service.Entity;

import java.util.List;
import java.util.Map;

public class TopicTagData {
    int topicId;
    String topicName;
    List<String> tag;
    List<Float> value;

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }


    public int getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<Float> getValue() {
        return value;
    }

    public void setValue(List<Float> value) {
        this.value = value;
    }

    public TopicTagData(int topicId, String topicName, List<String> tag, List<Float> value) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.tag = tag;
        this.value = value;
    }
}
