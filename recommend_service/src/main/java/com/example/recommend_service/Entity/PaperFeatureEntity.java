package com.example.recommend_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_feature", schema = "recommend")
@IdClass(PaperFeatureEntityPK.class)
public class PaperFeatureEntity {
    private int paperId;
    private int topicId;
    private Double degree;

    @Id
    @Column(name = "paper_id")
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
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
    public Double getDegree() {
        return degree;
    }

    public void setDegree(Double degree) {
        this.degree = degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperFeatureEntity that = (PaperFeatureEntity) o;
        return paperId == that.paperId && topicId == that.topicId && Objects.equals(degree, that.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, topicId, degree);
    }
}
