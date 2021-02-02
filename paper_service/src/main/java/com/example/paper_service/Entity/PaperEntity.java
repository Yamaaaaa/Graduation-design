package com.example.paper_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper", schema = "paper")
public class PaperEntity {
    private int id;
    private String title;
    private String abst;
    private Integer browseNum;
    private Integer recentBrowseNum;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "abst")
    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    @Basic
    @Column(name = "browse_num")
    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    @Basic
    @Column(name = "recent_browse_num")
    public Integer getRecentBrowseNum() {
        return recentBrowseNum;
    }

    public void setRecentBrowseNum(Integer recentBrowseNum) {
        this.recentBrowseNum = recentBrowseNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperEntity that = (PaperEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(abst, that.abst) && Objects.equals(browseNum, that.browseNum) && Objects.equals(recentBrowseNum, that.recentBrowseNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, abst, browseNum, recentBrowseNum);
    }

    public PaperEntity() {
    }

    public PaperEntity(String title, String abst, Integer browseNum, Integer recentBrowseNum) {
        this.title = title;
        this.abst = abst;
        this.browseNum = browseNum;
        this.recentBrowseNum = recentBrowseNum;
    }
}
