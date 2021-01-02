package com.example.paper_service.Entity;

import com.example.paper_service.Entity.PaperEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaperSimpleData implements Serializable {
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

    public PaperSimpleData(PaperEntity paperEntity) {
        this.paper_id = paperEntity.getId();
        //this.abst = paperEntity.getAbst();
        this.title = paperEntity.getTitle();
        this.totalBrowseNum = paperEntity.getBrowseNum();
    }

    public PaperSimpleData(int paper_id, String title, int totalBrowseNum){
        this.paper_id = paper_id;
        this.title = title;
        this.totalBrowseNum = totalBrowseNum;
    }

    public PaperSimpleData(){}

    int paper_id;
    String title;
    int totalBrowseNum;
}