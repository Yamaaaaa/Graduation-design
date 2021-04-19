package com.example.recommend_service.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Objects;

public class PaperSimpleEntity {
    private int id;
    private String title;
    private Integer browseNum;
    private Integer recentBrowseNum;

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

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Integer getRecentBrowseNum() {
        return recentBrowseNum;
    }

    public void setRecentBrowseNum(Integer recentBrowseNum) {
        this.recentBrowseNum = recentBrowseNum;
    }
}
