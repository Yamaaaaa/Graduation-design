package com.example.recommend_service.Entity;

import java.util.List;
import java.util.Set;

public class SquarePaperRecommendData {
    UserSubscribeData userSubscribeData;
    Set<String> tags;

    public UserSubscribeData getUserSubscribeData() {
        return userSubscribeData;
    }

    public void setUserSubscribeData(UserSubscribeData userSubscribeData) {
        this.userSubscribeData = userSubscribeData;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
