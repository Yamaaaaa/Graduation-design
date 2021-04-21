package com.example.recommend_service.Entity;

import java.util.List;
import java.util.Set;

public class SquarePaperRecommendData {
    UserSubscribeData userSubscribeData;
    List<String> tags;

    public UserSubscribeData getUserSubscribeData() {
        return userSubscribeData;
    }

    public void setUserSubscribeData(UserSubscribeData userSubscribeData) {
        this.userSubscribeData = userSubscribeData;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
