package com.example.recommend_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TopicTagRelationEntityPK implements Serializable {
    private int topicId;
    private String tagName;

    @Column(name = "topic_id")
    @Id
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Column(name = "tag_Name")
    @Id
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagId) {
        this.tagName = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicTagRelationEntityPK that = (TopicTagRelationEntityPK) o;
        return topicId == that.topicId && tagName == that.tagName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, tagName);
    }
}
