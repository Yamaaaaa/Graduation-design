package com.example.paper_service.Entity;

import java.util.List;
import java.util.Set;

public class PaperSimpleData {
    private PaperEntity paperEntity;
    private List<String> tags;

    public PaperEntity getPaperEntity() {
        return paperEntity;
    }

    public void setPaperEntity(PaperEntity paperEntity) {
        this.paperEntity = paperEntity;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

