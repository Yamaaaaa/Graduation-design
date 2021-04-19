package com.example.recommend_service.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_feature", schema = "recommend")
@IdClass(UserFeatureEntityPK.class)
public class UserFeatureEntity {
    private int userId;
    private int topicId;
    private Float degree;
    private Date lastDate;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "topic_id")
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Basic
    @Column(name = "degree")
    public Float getDegree() {
        return degree;
    }

    public void setDegree(Float degree) {
        this.degree = degree;
    }

    @Basic
    @Column(name = "last_date")
    public Date getLastDate(){return lastDate;}

    public void setLastDate(Date lastDate){this.lastDate = lastDate;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFeatureEntity that = (UserFeatureEntity) o;
        return userId == that.userId && topicId == that.topicId && Objects.equals(degree, that.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, topicId, degree);
    }

    public UserFeatureEntity(){ }

    public UserFeatureEntity(int userId, int topicId, Float degree, Date lastDate) {
        this.userId = userId;
        this.topicId = topicId;
        this.degree = degree;
        this.lastDate = lastDate;
    }
}
