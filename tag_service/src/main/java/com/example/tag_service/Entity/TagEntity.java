package com.example.tag_service.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tag", schema = "tag")
public class TagEntity {
    private int id;
    private String name;
    private Integer usedNum;
    private Date lastActiveTime;

    @Id
    @Column(name = "id")
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

    @Basic
    @Column(name = "used_num")
    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    @Basic
    @Column(name = "last_active_time")
    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tagEntity = (TagEntity) o;
        return id == tagEntity.id && Objects.equals(name, tagEntity.name) && Objects.equals(usedNum, tagEntity.usedNum) && Objects.equals(lastActiveTime, tagEntity.lastActiveTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, usedNum, lastActiveTime);
    }

    public TagEntity(){ }

    public TagEntity(String name, int usedNum, Date lastActiveTime){
        this.name = name;
        this.usedNum = usedNum;
        this.lastActiveTime = lastActiveTime;
    }
}
