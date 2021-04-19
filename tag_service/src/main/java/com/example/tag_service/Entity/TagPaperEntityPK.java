package com.example.tag_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class TagPaperEntityPK implements Serializable {
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

    @Id
    @Column(name = "tag_name")
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

        TagPaperEntityPK that = (TagPaperEntityPK) o;

        if (paperId != that.paperId) return false;
        if (tagName != that.tagName) return false;

        return true;
    }
}
