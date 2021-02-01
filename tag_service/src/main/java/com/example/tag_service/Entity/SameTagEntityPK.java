package com.example.tag_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SameTagEntityPK implements Serializable {
    private String name;
    private int sameTagId;

    @Column(name = "name")
    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "same_tag_id")
    @Id
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

        SameTagEntityPK that = (SameTagEntityPK) o;

        if (sameTagId != that.sameTagId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + sameTagId;
        return result;
    }
}
