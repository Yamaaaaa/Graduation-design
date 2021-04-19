package com.example.recommend_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "user_dislike_tag", schema = "recommend", catalog = "")
@IdClass(UserDislikeTagEntityPK.class)
public class UserDislikeTagEntity {
    private int userId;
    private String tagName;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDislikeTagEntity that = (UserDislikeTagEntity) o;

        if (userId != that.userId) return false;
        if (tagName != that.tagName) return false;

        return true;
    }
}
