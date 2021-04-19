package com.example.user_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "user_share", schema = "account", catalog = "")
@IdClass(UserShareEntityPK.class)
public class UserShareEntity {
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

    public UserShareEntity() {
    }

    public UserShareEntity(int userId, int paperId) {
        this.userId = userId;
        this.paperId = paperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserShareEntity that = (UserShareEntity) o;

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
