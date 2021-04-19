package com.example.recommend_service.Entity;

import java.util.List;

public class PaperTopicRankData {
    List<String> topicsName;
    List<Float> topicsRelate;

    public List<String> getTopicsName() {
        return topicsName;
    }

    public void setTopicsName(List<String> topicsName) {
        this.topicsName = topicsName;
    }

    public List<Float> getTopicsRelate() {
        return topicsRelate;
    }

    public void setTopicsRelate(List<Float> topicsRelate) {
        this.topicsRelate = topicsRelate;
    }
}
