package com.example.recommend_service.Entity;

import java.util.List;

public class TagManageData {
    List<String> sameTag;
    TagUsedData tagUsedData;
    PaperManageData paperManageData;

    public List<String> getSameTag() {
        return sameTag;
    }

    public void setSameTag(List<String> sameTag) {
        this.sameTag = sameTag;
    }

    public TagUsedData getTagUsedData() {
        return tagUsedData;
    }

    public void setTagUsedData(TagUsedData tagUsedData) {
        this.tagUsedData = tagUsedData;
    }

    public PaperManageData getPaperManageData() {
        return paperManageData;
    }

    public void setPaperManageData(PaperManageData paperManageData) {
        this.paperManageData = paperManageData;
    }
}
