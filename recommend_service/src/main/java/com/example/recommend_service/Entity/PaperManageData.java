package com.example.recommend_service.Entity;

import java.util.List;

public class PaperManageData {
    private List<SetItem> topicSet;
    private List<SetItem> tagSet;
    private List<PaperManageItemData> paperManageItemData;

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

    public List<PaperManageItemData> getPaperManageItemData() {
        return paperManageItemData;
    }

    public void setPaperManageItemData(List<PaperManageItemData> paperManageItemData) {
        this.paperManageItemData = paperManageItemData;
    }
}
