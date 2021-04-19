package com.example.user_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "user_dis", schema = "account", catalog = "")
@IdClass(UserDisEntityPK.class)
public class UserDisEntity {
    private int userId;
    private int disId;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "dis_id")
    public int getDisId() {
        return disId;
    }

    public void setDisId(int disId) {
        this.disId = disId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDisEntity that = (UserDisEntity) o;

        if (userId != that.userId) return false;
        if (disId != that.disId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + disId;
        return result;
    }

    public UserDisEntity() {
    }

    public UserDisEntity(int userId, int disId) {
        this.userId = userId;
        this.disId = disId;
    }
}
