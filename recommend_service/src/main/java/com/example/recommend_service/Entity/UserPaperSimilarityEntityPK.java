package com.example.recommend_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class UserPaperSimilarityEntityPK implements Serializable {
    private int userId;
    private int paperId;

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "paper_id")
    @Id
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPaperSimilarityEntityPK that = (UserPaperSimilarityEntityPK) o;

        if (userId != that.userId) return false;
        if (paperId != that.paperId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + paperId;
        return result;
    }
}
