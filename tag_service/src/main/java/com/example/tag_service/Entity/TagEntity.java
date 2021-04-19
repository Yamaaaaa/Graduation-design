package com.example.tag_service.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tag", schema = "tag")
public class TagEntity {
    private String name;
    private Integer usedNum;
    private Date lastActiveTime;

    @Id
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

    public TagEntity(){ }

    public TagEntity(String name, int usedNum, Date lastActiveTime){
        this.name = name;
        this.usedNum = usedNum;
        this.lastActiveTime = lastActiveTime;
    }

    public static TagEntity getNoneTagEntity(){
        return new TagEntity("不存在该标签", 0, new Date());
    }
}
