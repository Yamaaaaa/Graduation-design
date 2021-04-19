package com.example.recommend_service.Entity;

import java.util.List;

public class PaperManageItemData {
    int paperId;
    String paperTitle;
    int paperBrowse;
    int paperHot;
    private List<TopicSimpleData> paperTopics;
    private List<PaperTagData> paperTags;

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public int getPaperBrowse() {
        return paperBrowse;
    }

    public void setPaperBrowse(int paperBrowse) {
        this.paperBrowse = paperBrowse;
    }

    public int getPaperHot() {
        return paperHot;
    }

    public void setPaperHot(int paperHot) {
        this.paperHot = paperHot;
    }

    public List<TopicSimpleData> getPaperTopics() {
        return paperTopics;
    }

    public void setPaperTopics(List<TopicSimpleData> paperTopics) {
        this.paperTopics = paperTopics;
    }

    public List<PaperTagData> getPaperTags() {
        return paperTags;
    }

    public void setPaperTags(List<PaperTagData> paperTags) {
        this.paperTags = paperTags;
    }
}
