package com.example.recommend_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "topic_tag_relation", schema = "recommend")
@IdClass(TopicTagRelationEntityPK.class)
public class TopicTagRelationEntity {
    private int topicId;
    private int tagId;
    private Double degree;

    @Id
    @Column(name = "topic_id")
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Id
    @Column(name = "tag_id")
    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
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
        TopicTagRelationEntity that = (TopicTagRelationEntity) o;
        return topicId == that.topicId && tagId == that.tagId && Objects.equals(degree, that.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, tagId, degree);
    }

    public TopicTagRelationEntity() {
    }

    public TopicTagRelationEntity(int topicId, int tagId, Double degree) {
        this.topicId = topicId;
        this.tagId = tagId;
        this.degree = degree;
    }
}
