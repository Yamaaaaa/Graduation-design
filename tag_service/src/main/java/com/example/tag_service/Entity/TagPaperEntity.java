package com.example.tag_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "tag_paper", schema = "tag")
@IdClass(TagPaperEntityPK.class)
public class TagPaperEntity {
    private int paperId;
    private String tagName;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagPaperEntity that = (TagPaperEntity) o;

        if (paperId != that.paperId) return false;
        if (tagName != that.tagName) return false;
        return true;
    }

    public TagPaperEntity(int paperId, String tagName) {
        this.paperId = paperId;
        this.tagName = tagName;
    }

    public TagPaperEntity() {
    }
}
