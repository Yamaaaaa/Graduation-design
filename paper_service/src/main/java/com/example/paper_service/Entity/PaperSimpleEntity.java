package com.example.paper_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper", schema = "paper")
public class PaperSimpleEntity {
    private int id;
    private String title;
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

        PaperSimpleEntity that = (PaperSimpleEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(browseNum, that.browseNum)) return false;
        if (!Objects.equals(recentBrowseNum, that.recentBrowseNum))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (browseNum != null ? browseNum.hashCode() : 0);
        result = 31 * result + (recentBrowseNum != null ? recentBrowseNum.hashCode() : 0);
        return result;
    }
}
