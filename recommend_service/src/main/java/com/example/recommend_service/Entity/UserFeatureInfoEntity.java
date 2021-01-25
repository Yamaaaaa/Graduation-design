package com.example.recommend_service.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_feature_info", schema = "recommend")
public class UserFeatureInfoEntity {
    int user_id;
    int browseNum;
    boolean renew;
    Date lastRenewDate;

    @Id
    @Column(name = "user_id")
    public int getId() {
        return user_id;
    }

    public void setId(int user_id) {
        this.user_id = user_id;
    }

    @Basic
    @Column(name = "browse_num")
    public int getBrowseNum(){return browseNum;}

    public void setBrowseNum(int browseNum){this.browseNum = browseNum;}

    @Basic
    @Column(name = "renew")
    public boolean getRenew() {
        return renew;
    }

    public void setRenew(boolean renew) {
        this.renew = renew;
    }

    @Basic
    @Column(name = "last_renew_date")
    public Date getLastRenewDate(){return lastRenewDate;}

    public void setLastRenewDate(Date lastRenewDate){this.lastRenewDate = lastRenewDate;}
}
