package com.example.paper_service.Entity;

import java.util.List;
import java.util.Set;

public class PaperImportData {
    int id;
    String title;
    String abst;
    Set<String> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public PaperImportData() {
    }

    public PaperImportData(String title, String abst, Set<String> tags) {
        this.title = title;
        this.abst = abst;
        this.tags = tags;
    }
}
