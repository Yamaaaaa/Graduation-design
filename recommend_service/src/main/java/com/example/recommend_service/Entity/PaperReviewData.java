package com.example.recommend_service.Entity;

import java.util.List;

public class PaperReviewData {
    int paperId;
    String paperTitle;
    String paperAbstract;
    List<TagName> uncheckTag;
    PaperTopicRankData paperTopicRankData;
    PaperTopicRankData paperTagRankData;
    TopicTagRelateData topicTagRelateData;

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

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public List<TagName> getUncheckTag() {
        return uncheckTag;
    }

    public void setUncheckTag(List<TagName> uncheckTag) {
        this.uncheckTag = uncheckTag;
    }

    public PaperTopicRankData getPaperTopicRankData() {
        return paperTopicRankData;
    }

    public void setPaperTopicRankData(PaperTopicRankData paperTopicRankData) {
        this.paperTopicRankData = paperTopicRankData;
    }

    public PaperTopicRankData getPaperTagRankData() {
        return paperTagRankData;
    }

    public void setPaperTagRankData(PaperTopicRankData paperTagRankData) {
        this.paperTagRankData = paperTagRankData;
    }

    public TopicTagRelateData getTopicTagRelateData() {
        return topicTagRelateData;
    }

    public void setTopicTagRelateData(TopicTagRelateData topicTagRelateData) {
        this.topicTagRelateData = topicTagRelateData;
    }
}
