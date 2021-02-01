package com.example.recommend_service.Entity;

public class SameTagEntity {
    private String name;
    private int sameTagId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSameTagId() {
        return sameTagId;
    }

    public void setSameTagId(int sameTagId) {
        this.sameTagId = sameTagId;
    }

    public SameTagEntity(String name, int sameTagId) {
        this.name = name;
        this.sameTagId = sameTagId;
    }

    public SameTagEntity() {
    }
}