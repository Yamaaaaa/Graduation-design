package com.example.recommend_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "user_similarity", schema = "recommend", catalog = "")
@IdClass(UserSimilarityEntityPK.class)
public class UserSimilarityEntity {
    private int userId;
    private int otherUser;
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
    @Column(name = "other_user")
    public int getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(int otherUser) {
        this.otherUser = otherUser;
    }

    @Basic
    @Column(name = "relateValue")
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

        UserSimilarityEntity that = (UserSimilarityEntity) o;

        if (userId != that.userId) return false;
        if (otherUser != that.otherUser) return false;
        if (relateValue != null ? !relateValue.equals(that.relateValue) : that.relateValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + otherUser;
        result = 31 * result + (relateValue != null ? relateValue.hashCode() : 0);
        return result;
    }
}
