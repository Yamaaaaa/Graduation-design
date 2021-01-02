package com.example.tag_service.Servcie;

import com.example.tag_service.Dao.TagDao;
import com.example.tag_service.Entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TagService {
    @Autowired
    TagDao tagDao;

    public Map<Integer, String> getTagList(List<Integer> tagIDList){
        Map<Integer, String> tagList = new HashMap<Integer, String>();
        for(Integer tagID: tagIDList){
            tagList.put(tagID, tagDao.findById((int)tagID).getName());
        }
        return tagList;
    }

    public void useTag(List<Integer> tagIDList){
        for(Integer tagId: tagIDList){
            TagEntity tagEntity = tagDao.findById((int)tagId);
            tagEntity.setUsedNum(tagEntity.getUsedNum() + 1);
            tagEntity.setLastActiveTime(new Date());
        }
    }

    public void addNewTag(String tag){
        TagEntity tagEntity = new TagEntity(tag, 1, new Date());
        tagDao.save(tagEntity);
    }

    public void addTag(List<String> tags){
        List<Integer> existTag = new ArrayList<>();
        for(String tag: tags){
            if(tagDao.existsByName(tag)){
                existTag.add(tagDao.findByName(tag).getId());
            }else
                addNewTag(tag);
        }
        useTag(existTag);
    }
}
