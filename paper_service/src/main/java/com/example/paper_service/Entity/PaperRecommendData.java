package com.example.paper_service.Entity;

import java.util.List;
import java.util.Map;

public class PaperRecommendData {
    Map<Integer, String> tags;
    List<Integer> topics;

    public PaperRecommendData(Map<Integer, String> tags, List<Integer> topics) {
        this.tags = tags;
        this.topics = topics;
    }

    public Map<Integer, String> getTags() {
        return tags;
    }

    public List<Integer> getTopics() {
        return topics;
    }

    public void setTags(Map<Integer, String> tags) {
        this.tags = tags;
    }

    public void setTopics(List<Integer> topics) {
        this.topics = topics;
    }
}