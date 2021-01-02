package com.example.recommend_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PaperFeatureEntityPK implements Serializable {
    private int paperId;
    private int topicId;

    @Column(name = "paper_id")
    @Id
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
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
        PaperFeatureEntityPK that = (PaperFeatureEntityPK) o;
        return paperId == that.paperId && topicId == that.topicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, topicId);
    }
}
