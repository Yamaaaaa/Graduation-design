package com.example.paper_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sys_info", schema = "paper", catalog = "")
public class SysInfoEntity {
    private String name;
    private Integer val;

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "val")
    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysInfoEntity that = (SysInfoEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, val);
    }
}
