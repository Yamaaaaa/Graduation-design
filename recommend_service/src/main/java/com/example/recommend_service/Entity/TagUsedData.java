package com.example.recommend_service.Entity;

import java.util.List;

public class TagUsedData {
    List<String> lastCalender;
    List<Integer> lastUsedNum;

    public List<String> getLastCalender() {
        return lastCalender;
    }

    public void setLastCalender(List<String> lastCalender) {
        this.lastCalender = lastCalender;
    }

    public List<Integer> getLastUsedNum() {
        return lastUsedNum;
    }

    public void setLastUsedNum(List<Integer> lastUsedNum) {
        this.lastUsedNum = lastUsedNum;
    }
}
