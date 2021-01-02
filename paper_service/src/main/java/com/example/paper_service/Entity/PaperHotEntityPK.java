package com.example.paper_service.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PaperHotEntityPK implements Serializable {
    private int paperId;
    private int serNum;

    @Column(name = "paper_id")
    @Id
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Column(name = "ser_num")
    @Id
    public int getSerNum() {
        return serNum;
    }

    public void setSerNum(int serNum) {
        this.serNum = serNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperHotEntityPK that = (PaperHotEntityPK) o;
        return paperId == that.paperId && serNum == that.serNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, serNum);
    }
}
