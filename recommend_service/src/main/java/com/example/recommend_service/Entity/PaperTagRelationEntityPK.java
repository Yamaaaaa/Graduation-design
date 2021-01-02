package com.example.recommend_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PaperTagRelationEntityPK implements Serializable {
    private int paperId;
    private int tagId;

    @Column(name = "paper_id")
    @Id
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Column(name = "tag_id")
    @Id
    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperTagRelationEntityPK that = (PaperTagRelationEntityPK) o;
        return paperId == that.paperId && tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, tagId);
    }
}
