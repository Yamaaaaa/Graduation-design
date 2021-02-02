package com.example.tag_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class TagPaperEntityPK implements Serializable {
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

        TagPaperEntityPK that = (TagPaperEntityPK) o;

        if (paperId != that.paperId) return false;
        if (tagId != that.tagId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paperId;
        result = 31 * result + tagId;
        return result;
    }
}
