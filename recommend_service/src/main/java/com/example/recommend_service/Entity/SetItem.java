package com.example.recommend_service.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetItem{
    String text;
    String value;

    public SetItem(String text) {
        this.text = text;
        this.value = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    static public List<SetItem> getSetItemList(Set<String> data){
        List<SetItem> result = new ArrayList<>();
        for(String d: data){
            result.add(new SetItem(d));
        }
        return result;
    }
}
