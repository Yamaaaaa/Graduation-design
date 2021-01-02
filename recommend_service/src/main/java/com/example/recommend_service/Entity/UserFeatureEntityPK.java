package com.example.recommend_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserFeatureEntityPK implements Serializable {
    private int userId;
    private int topicId;

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "topic_id")
    @Id
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFeatureEntityPK that = (UserFeatureEntityPK) o;
        return userId == that.userId && topicId == that.topicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, topicId);
    }
}
