package com.example.recommend_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserHistoryEntityPK implements Serializable {
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
        UserHistoryEntityPK that = (UserHistoryEntityPK) o;
        return userId == that.userId && paperId == that.paperId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, paperId);
    }
}
