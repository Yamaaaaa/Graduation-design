package com.example.recommend_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_tag_relation", schema = "recommend")
@IdClass(PaperTagRelationEntityPK.class)
public class PaperTagRelationEntity {
    private int paperId;
    private int tagId;
    private Double degree;

    @Id
    @Column(name = "paper_id")
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Id
    @Column(name = "tag_id")
    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "degree")
    public Double getDegree() {
        return degree;
    }

    public void setDegree(Double degree) {
        this.degree = degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperTagRelationEntity that = (PaperTagRelationEntity) o;
        return paperId == that.paperId && tagId == that.tagId && Objects.equals(degree, that.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, tagId, degree);
    }
}
