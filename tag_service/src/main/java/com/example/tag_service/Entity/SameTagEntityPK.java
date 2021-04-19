package com.example.tag_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SameTagEntityPK implements Serializable {
    private String name;
    private String sameTagName;

    @Column(name = "name")
    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "same_tag_name")
    @Id
    public String getSameTagName() {
        return sameTagName;
    }

    public void setSameTagName(String sameTagName) {
        this.sameTagName = sameTagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SameTagEntityPK that = (SameTagEntityPK) o;

        if (sameTagName != that.sameTagName) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }
}
