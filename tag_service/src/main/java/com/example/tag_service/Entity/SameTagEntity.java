package com.example.tag_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "same_tag", schema = "tag", catalog = "")
@IdClass(SameTagEntityPK.class)
public class SameTagEntity {
    private String name;
    private int sameTagId;

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "same_tag_id")
    public int getSameTagId() {
        return sameTagId;
    }

    public void setSameTagId(int sameTagId) {
        this.sameTagId = sameTagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SameTagEntity that = (SameTagEntity) o;

        if (sameTagId != that.sameTagId) return false;
        if (!Objects.equals(name, that.name)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + sameTagId;
        return result;
    }

    public SameTagEntity() {
    }

    public SameTagEntity(String name, int sameTagId) {
        this.name = name;
        this.sameTagId = sameTagId;
    }
}
