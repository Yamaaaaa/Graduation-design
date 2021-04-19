package com.example.recommend_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "user_dislike_paper", schema = "recommend", catalog = "")
@IdClass(UserDislikePaperEntityPK.class)
public class UserDislikePaperEntity {
    private int userId;
    private int paperId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDislikePaperEntity that = (UserDislikePaperEntity) o;

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
