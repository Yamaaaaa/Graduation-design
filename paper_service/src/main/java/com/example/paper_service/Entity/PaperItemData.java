package com.example.paper_service.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaperItemData {
    int paper_id;
    String title;
    int totalBrowseNum;
    List<Integer> topics;
    Map<Integer, String> tagList = new HashMap<>();

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public int getTotalBrowseNum() {
        return totalBrowseNum;
    }

    public void setTotalBrowseNum(int totalBrowseNum) {
        this.totalBrowseNum = totalBrowseNum;
    }

    public Map<Integer, String> getTagList() {
        return tagList;
    }

    public void setTagList(Map<Integer, String> tagList) {
        this.tagList = tagList;
    }

    public void setTopics(List<Integer> topics) {
        this.topics = topics;
    }

    public List<Integer> getTopics() {
        return topics;
    }

    public PaperItemData(PaperEntity paperEntity, PaperRecommendData paperRecommendData) {
        this.paper_id = paperEntity.getId();

        this.title = paperEntity.getTitle();
        this.totalBrowseNum = paperEntity.getBrowseNum();
        this.tagList = paperRecommendData.getTags();
        this.topics = paperRecommendData.getTopics();
    }

    public PaperItemData(){}
}
