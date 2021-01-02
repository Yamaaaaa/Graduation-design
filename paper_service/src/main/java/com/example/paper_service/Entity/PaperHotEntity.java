package com.example.paper_service.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_hot", schema = "paper")
@IdClass(PaperHotEntityPK.class)
public class PaperHotEntity {
    private int paperId;
    private int serNum;
    private Integer browseNum;

    @Id
    @Column(name = "paper_id")
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Id
    @Column(name = "ser_num")
    public int getSerNum() {
        return serNum;
    }

    public void setSerNum(int serNum) {
        this.serNum = serNum;
    }

    @Basic
    @Column(name = "browse_num")
    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperHotEntity that = (PaperHotEntity) o;
        return paperId == that.paperId && serNum == that.serNum && Objects.equals(browseNum, that.browseNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, serNum, browseNum);
    }
}
