package com.example.recommend_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "tag_paper", schema = "recommend", catalog = "")
@IdClass(TagPaperEntityPK.class)
public class TagPaperEntity {
    private int paperId;
    private int tagId;
    private Byte uncheck;

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
    @Column(name = "uncheck")
    public Byte getUncheck() {
        return uncheck;
    }

    public void setUncheck(Byte uncheck) {
        this.uncheck = uncheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagPaperEntity that = (TagPaperEntity) o;

        if (paperId != that.paperId) return false;
        if (tagId != that.tagId) return false;
        if (uncheck != null ? !uncheck.equals(that.uncheck) : that.uncheck != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paperId;
        result = 31 * result + tagId;
        result = 31 * result + (uncheck != null ? uncheck.hashCode() : 0);
        return result;
    }
}
