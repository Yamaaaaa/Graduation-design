package com.example.recommend_service.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_history", schema = "recommend")
@IdClass(UserHistoryEntityPK.class)
public class UserHistoryEntity {
    private int userId;
    private int paperId;
    private Date browseTime;
    private boolean uncheck;

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
    @Column(name = "browse_time")
    public Date getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }

    @Basic
    @Column(name = "uncheck")
    public boolean getUncheck(){return uncheck;}

    public void setUncheck(boolean uncheck){this.uncheck = uncheck;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHistoryEntity that = (UserHistoryEntity) o;
        return userId == that.userId && paperId == that.paperId && Objects.equals(browseTime, that.browseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, paperId, browseTime);
    }

    public UserHistoryEntity() {
    }

    public UserHistoryEntity(int userId, int paperId, Date browseTime, boolean uncheck) {
        this.userId = userId;
        this.paperId = paperId;
        this.browseTime = browseTime;
        this.uncheck = uncheck;
    }
}
