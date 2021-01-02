package com.example.recommend_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_info", schema = "recommend")
public class PaperInfoEntity {
    private int id;
    private Integer taggedNum;
    private Integer uncheckNum;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "tagged_num")
    public Integer getTaggedNum() {
        return taggedNum;
    }

    public void setTaggedNum(Integer taggedNum) {
        this.taggedNum = taggedNum;
    }

    @Basic
    @Column(name = "uncheck_num")
    public Integer getUncheckNum() {
        return uncheckNum;
    }

    public void setUncheckNum(Integer uncheckNum) {
        this.uncheckNum = uncheckNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperInfoEntity that = (PaperInfoEntity) o;
        return id == that.id && Objects.equals(taggedNum, that.taggedNum) && Objects.equals(uncheckNum, that.uncheckNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taggedNum, uncheckNum);
    }
}
