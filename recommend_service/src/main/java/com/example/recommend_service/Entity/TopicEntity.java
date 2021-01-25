package com.example.recommend_service.Entity;

import javax.persistence.*;

@Entity
@Table(name = "topic", schema = "recommend")
public class TopicEntity {
    int id;
    String name;

    @Id
    @Column(name = "topic_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
