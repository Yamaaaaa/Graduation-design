package com.example.recommend_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class UserSimilarityEntityPK implements Serializable {
    private int userId;
    private int otherUser;

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "other_user")
    @Id
    public int getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(int otherUser) {
        this.otherUser = otherUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSimilarityEntityPK that = (UserSimilarityEntityPK) o;

        if (userId != that.userId) return false;
        if (otherUser != that.otherUser) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + otherUser;
        return result;
    }
}
