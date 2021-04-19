package com.example.recommend_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_tag_relation", schema = "recommend")
@IdClass(PaperTagRelationEntityPK.class)
public class PaperTagRelationEntity {
    private int paperId;
    private String tagName;
    private Float degree;
    private int tagNum;
    private boolean renew;

    @Id
    @Column(name = "paper_id")
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Id
    @Column(name = "tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Basic
    @Column(name = "degree")
    public Float getDegree() {
        return degree;
    }

    public void setDegree(Float degree) {
        this.degree = degree;
    }

    @Column(name = "tag_num")
    public int getTagNum() {
        return tagNum;
    }

    public void setTagNum(int tagNum) {
        this.tagNum = tagNum;
    }

    @Column(name = "renew")
    public boolean getRenew() {
        return renew;
    }

    public void setRenew(boolean tagNum) {
        this.renew = renew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperTagRelationEntity that = (PaperTagRelationEntity) o;
        return paperId == that.paperId && tagName == that.tagName && Objects.equals(degree, that.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, tagName, degree);
    }

    public PaperTagRelationEntity(int paperId, String tagName) {
        this.paperId = paperId;
        this.tagName = tagName;
        this.tagNum = 1;
        this.renew = false;
    }

    public PaperTagRelationEntity(int paperId, String tagName, Float degree, int tagNum) {
        this.paperId = paperId;
        this.tagName = tagName;
        this.degree = degree;
        this.tagNum = tagNum;
        this.renew = true;
    }

    public PaperTagRelationEntity() {
    }
}
