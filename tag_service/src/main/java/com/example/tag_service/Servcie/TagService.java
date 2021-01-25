package com.example.tag_service.Servcie;

import com.example.tag_service.Dao.TagDao;
import com.example.tag_service.Entity.TagEntity;
import com.example.tag_service.Util.HttpJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class TagService {
    @Autowired
    TagDao tagDao;
    @Autowired
    private RestTemplate restTemplate;

    private String addPaperTagUrl = "addPaperTag";

    public Map<Integer, String> getTagList(List<Integer> tagIDList){
        Map<Integer, String> tagList = new HashMap<Integer, String>();
        for(Integer tagID: tagIDList){
            tagList.put(tagID, tagDao.findById((int)tagID).getName());
        }
        return tagList;
    }

    public void addTag(int paperId, List<String> tags){
        List<Integer> tagId = new ArrayList<>();
        for(String tag: tags){
            TagEntity tagEntity;
            if(tagDao.existsByName(tag)){
                tagEntity = tagDao.findByName(tag);
                tagEntity.setUsedNum(tagEntity.getUsedNum() + 1);
                tagEntity.setLastActiveTime(new Date());

            }else
                tagEntity = new TagEntity(tag, 1, new Date());
            tagEntity = tagDao.save(tagEntity);
            tagId.add(tagEntity.getId());
        }
        restTemplate.postForObject(addPaperTagUrl, tagId, void.class);
    }

    public void deleteTag(List<Integer> tags){
        for(Integer tag_id: tags){
            tagDao.deleteById(tag_id);
        }
    }

    public List<TagEntity> pageTag(int pageNum, int pageSize){
        List<TagEntity> tagEntities = tagDao.findAll(PageRequest.of(pageNum, pageSize)).getContent();
        return tagEntities;
    }
}
