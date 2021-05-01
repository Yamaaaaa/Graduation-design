package com.example.recommend_service.Entity;

import javax.persistence.*;

public class TagPaperEntity {
    private int paperId;
    private String tagName;

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
