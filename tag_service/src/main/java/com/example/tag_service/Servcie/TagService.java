package com.example.tag_service.Servcie;

import com.example.tag_service.Dao.SameTagDao;
import com.example.tag_service.Dao.TagDao;
import com.example.tag_service.Dao.TagPaperDao;
import com.example.tag_service.Entity.SameTagEntity;
import com.example.tag_service.Entity.TagEntity;
import com.example.tag_service.Entity.TagPaperEntity;
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
    SameTagDao sameTagDao;
    @Autowired
    TagPaperDao tagPaperDao;
    @Autowired
    private RestTemplate restTemplate;

    private String addPaperTagUrl = "addPaperTag";
    private String mergeTagUrl = "mergeTag";
    private String deleteTagRelation = "deleteTag";

    public List<String> getTagList(List<Integer> tagIDList){
        return tagDao.getNameByIdList(tagIDList);
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

    public void mergeTag(int goalTag, int operateTag){
        TagEntity tagEntity = tagDao.findById(operateTag);
        sameTagDao.save(new SameTagEntity(tagEntity.getName(), goalTag));
        tagDao.deleteById(operateTag);
        Map<String, Object> data = new HashMap<>();
        data.put("goalTag", goalTag);
        data.put("operateTag", operateTag);
        restTemplate.getForObject(HttpJob.generateRequestParameters("http", mergeTagUrl, data), void.class);
    }

    public TagEntity getTagInfo(int tagId){
        return tagDao.findById(tagId);
    }

    public List<String> getSameTagData(int tagId){
        return sameTagDao.getSameTagNameByTagId(tagId);
    }

    public List<Integer> getUncheckList(){
        return tagPaperDao.getAllPaperId();
    }

    public List<Integer> getUncheckTagId(int paperId){
        return tagPaperDao.findAllByPaperId(paperId);
    }

    public void checkTagPaper(int paperId, Set<String> tags){
        tagPaperDao.deleteByPaperId(paperId);
        List<Integer> tagId = new ArrayList<>();
        tagId.add(paperId);
        for(String tag: tags){
            TagEntity tagEntity;
            if(tagDao.existsByName(tag)){
                tagEntity = tagDao.findByName(tag);
                tagEntity.setUsedNum(tagEntity.getUsedNum() + 1);
                tagEntity.setLastActiveTime(new Date());

            }else{
                tagEntity = new TagEntity(tag, 1, new Date());
            }
            tagEntity = tagDao.save(tagEntity);
            tagId.add(tagEntity.getId());
        }
        restTemplate.postForObject(addPaperTagUrl, tagId, void.class);
    }

    public void addTag(int paperId, List<String> tags){
        for(String tag: tags){
            tagPaperDao.save(new TagPaperEntity(paperId, tag));
        }
    }

    public void refreshTag(){
        //int userActionDate = sysInfoDao.findByName("user_action_date").getVal();
        Date da = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(da);//把当前时间赋给日历
        calendar.add(calendar.MONTH, -2);
        da = calendar.getTime();
        List<TagEntity> tagEntities = tagDao.findByLastActiveTimeBeforeAndUsedNumLessThan(da, 10);
        List<Integer> tagIdList = new ArrayList<>();
        for(TagEntity tagEntity: tagEntities){
            tagIdList.add(tagEntity.getId());
        }
        restTemplate.postForObject(deleteTagRelation, tagIdList, void.class);
        tagDao.deleteByLastActiveTimeBeforeAndUsedNumLessThan(da, 10);
    }

    public List<TagEntity> searchTag(String searchText){
        List<TagEntity> searchResult = new ArrayList<>();
        searchResult.addAll(tagDao.findAllByNameContains(searchText));
        List<Integer> tagIdList = sameTagDao.findAllByNameContains(searchText);
        for(Integer tagId: tagIdList){
            searchResult.add(tagDao.findById((int)tagId));
        }

        return searchResult;
    }
}
