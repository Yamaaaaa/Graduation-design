package com.example.user_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "user_subscribe", schema = "account", catalog = "")
@IdClass(UserSubscribeEntityPK.class)
public class UserSubscribeEntity {
    private int userId;
    private int subId;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "sub_id")
    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public UserSubscribeEntity() {
    }

    public UserSubscribeEntity(int userId, int subId) {
        this.userId = userId;
        this.subId = subId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscribeEntity that = (UserSubscribeEntity) o;

        if (userId != that.userId) return false;
        if (subId != that.subId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + subId;
        return result;
    }
}
