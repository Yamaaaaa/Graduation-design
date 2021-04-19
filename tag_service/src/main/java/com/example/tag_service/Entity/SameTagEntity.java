package com.example.tag_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "same_tag", schema = "tag", catalog = "")
@IdClass(SameTagEntityPK.class)
public class SameTagEntity {
    private String name;
    private String sameTagName;

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "same_tag_name")
    public String getSameTagName() {
        return sameTagName;
    }

    public void setSameTagName(String sameTagId) {
        this.sameTagName = sameTagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SameTagEntity that = (SameTagEntity) o;

        if (sameTagName != that.sameTagName) return false;
        if (!Objects.equals(name, that.name)) return false;
        return true;
    }


    public SameTagEntity() {
    }

    public SameTagEntity(String name, String sameTagName) {
        this.name = name;
        this.sameTagName = sameTagName;
    }
}
