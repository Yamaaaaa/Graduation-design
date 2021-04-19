package com.example.recommend_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PaperTagRelationEntityPK implements Serializable {
    private int paperId;
    private String tagName;

    @Column(name = "paper_id")
    @Id
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Column(name = "tag_Name")
    @Id
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperTagRelationEntityPK that = (PaperTagRelationEntityPK) o;
        return paperId == that.paperId && tagName == that.tagName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, tagName);
    }
}
