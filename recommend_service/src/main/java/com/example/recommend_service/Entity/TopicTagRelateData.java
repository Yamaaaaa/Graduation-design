package com.example.recommend_service.Entity;

import java.util.List;
import java.util.Set;

public class TopicTagRelateData {
    List<String> topics;
    List<String> tags;
    List<List<Float>> values;

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<List<Float>> getValues() {
        return values;
    }

    public void setValues(List<List<Float>> values) {
        this.values = values;
    }
}
