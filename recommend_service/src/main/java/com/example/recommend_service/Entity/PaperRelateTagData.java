package com.example.recommend_service.Entity;

import java.util.List;

public class PaperRelateTagData {
    int paperId;
    List<String> tags;

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
