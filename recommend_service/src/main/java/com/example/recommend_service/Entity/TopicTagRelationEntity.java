package com.example.recommend_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "topic_tag_relation", schema = "recommend")
@IdClass(TopicTagRelationEntityPK.class)
public class TopicTagRelationEntity {
    private int topicId;
    private String tagName;
    private Float degree;

    @Id
    @Column(name = "topic_id")
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Id
    @Column(name = "tag_Name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Basic
    @Column(name = "degree")
    public Float getDegree() {
        return degree;
    }

    public void setDegree(Float degree) {
        this.degree = degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicTagRelationEntity that = (TopicTagRelationEntity) o;
        return topicId == that.topicId && tagName == that.tagName && Objects.equals(degree, that.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, tagName, degree);
    }

    public TopicTagRelationEntity() {
    }

    public TopicTagRelationEntity(int topicId, String tagName, Float degree) {
        this.topicId = topicId;
        this.tagName = tagName;
        this.degree = degree;
    }
}
