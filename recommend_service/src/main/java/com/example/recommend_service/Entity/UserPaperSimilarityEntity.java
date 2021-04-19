package com.example.recommend_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "user_paper_similarity", schema = "recommend", catalog = "")
@IdClass(UserPaperSimilarityEntityPK.class)
public class UserPaperSimilarityEntity {
    private int userId;
    private int paperId;
    private Float relateValue;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "paper_id")
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Basic
    @Column(name = "relate_value")
    public Float getRelateValue() {
        return relateValue;
    }

    public void setRelateValue(Float relateValue) {
        this.relateValue = relateValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPaperSimilarityEntity that = (UserPaperSimilarityEntity) o;

        if (userId != that.userId) return false;
        if (paperId != that.paperId) return false;
        if (relateValue != null ? !relateValue.equals(that.relateValue) : that.relateValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + paperId;
        result = 31 * result + (relateValue != null ? relateValue.hashCode() : 0);
        return result;
    }
}
