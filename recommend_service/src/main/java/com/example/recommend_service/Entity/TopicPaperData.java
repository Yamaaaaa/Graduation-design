package com.example.recommend_service.Entity;

import java.util.List;

public class TopicPaperData {
    private int topicId;
    private String topicName;
    private List<SetItem> topicSet;
    private List<SetItem> tagSet;
    private List<PaperData> paperData;

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

    public List<SetItem> getTopicSet() {
        return topicSet;
    }

    public void setTopicSet(List<SetItem> topicSet) {
        this.topicSet = topicSet;
    }

    public List<SetItem> getTagSet() {
        return tagSet;
    }

    public void setTagSet(List<SetItem> tagSet) {
        this.tagSet = tagSet;
    }

    public List<PaperData> getPaperData() {
        return paperData;
    }

    public void setPaperData(List<PaperData> paperData) {
        this.paperData = paperData;
    }
}
