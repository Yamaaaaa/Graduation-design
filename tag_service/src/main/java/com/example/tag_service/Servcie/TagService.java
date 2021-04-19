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

import javax.transaction.Transactional;
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

    private String recommendService = "http://localhost:50002/";
    private String initTag = "initTagData";
    private String addPaperTagUrl = "addPaperTag";
    private String mergeTagUrl = "mergeTag";
    private String deleteTagRelation = "deleteTag";

    public void deleteTag(List<String> tags){
        for(String tag: tags){
            tagDao.deleteByName(tag);
        }
        restTemplate.postForObject(deleteTagRelation, tags, void.class);
    }

    public List<TagEntity> pageTag(){
        return tagDao.findAll();
    }

    public List<String> getAllTag(){
        List<String> result = new ArrayList<>();
        for(TagEntity tagEntity : tagDao.findAll()){
            result.add(tagEntity.getName());
        }
        return result;
    }

    @Transactional
    public void mergeTag(String goalTag, List<String> operateTags){
        for(String operateTag: operateTags) {
            sameTagDao.save(new SameTagEntity(operateTag, goalTag));
            tagDao.deleteByName(operateTag);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("goalTag", goalTag);
        data.put("operateTag", operateTags);
        restTemplate.postForObject(recommendService+mergeTagUrl, data, void.class);
    }

    public TagEntity getTagInfo(String tagId){
        return tagDao.findByName(tagId);
    }

    public List<String> getSameTagData(String tagId){
        return sameTagDao.findNameBySameTagName(tagId);
    }

    public List<Integer> getUncheckList(){
        return tagPaperDao.getAllPaperId();
    }

    @Transactional
    public void checkTagPaper(int paperId, Set<String> tags){
        for(String tag: tags){
            tagPaperDao.deleteByPaperIdAndTagName(paperId, tag);
            TagEntity tagEntity;
            if(tagDao.existsByName(tag)){
                tagEntity = tagDao.findByName(tag);
                tagEntity.setUsedNum(tagEntity.getUsedNum() + 1);
                tagEntity.setLastActiveTime(new Date());

            }else{
                tagEntity = new TagEntity(tag, 1, new Date());
            }
            tagEntity = tagDao.save(tagEntity);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("paperId", paperId);
        map.put("tags", tags);
        restTemplate.postForObject(addPaperTagUrl, map, void.class);
    }

    public void refreshTag(){
        //int userActionDate = sysInfoDao.findByName("user_action_date").getVal();
        Date da = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(da);//把当前时间赋给日历
        calendar.add(calendar.MONTH, -2);
        da = calendar.getTime();
        List<TagEntity> tagEntities = tagDao.findByLastActiveTimeBeforeAndUsedNumLessThan(da, 10);
        List<String> tagIdList = new ArrayList<>();
        for(TagEntity tagEntity: tagEntities){
            tagIdList.add(tagEntity.getName());
        }
        restTemplate.postForObject(deleteTagRelation, tagIdList, void.class);
        tagDao.deleteByLastActiveTimeBeforeAndUsedNumLessThan(da, 10);
    }

    public List<TagEntity> searchTag(String searchText){
        System.out.print("searchText: "+searchText);
        List<TagEntity> searchResult = new ArrayList<>();
        searchResult.addAll(tagDao.findAllByNameContains(searchText));
        List<String> tagIdList = sameTagDao.findDistinctByNameContains(searchText);
        for(String tagId: tagIdList){
            searchResult.add(tagDao.findByName(tagId));
        }

        return searchResult;
    }

    public List<String> getUncheckTag(int paperId){
        return tagPaperDao.findAllByPaperId(paperId);
    }

    public void addUncheckTag(Map<Integer, Set<String>> tagPaperData){
        System.out.println("tagPaperData" + tagPaperData);
        for(Map.Entry<Integer, Set<String>> tagPaper: tagPaperData.entrySet()){
            assert tagPaper.getKey()!=0;
            for(String tag: tagPaper.getValue()){
                TagPaperEntity tagPaperEntity = new TagPaperEntity();
                tagPaperEntity.setPaperId(tagPaper.getKey());
                tagPaperEntity.setTagName(tag);
                tagPaperDao.save(tagPaperEntity);
            }
        }
    }

    @Transactional
    public void initTag(){
        List<TagPaperEntity> tagEntityList = tagPaperDao.findAll();
        Map<Integer, List<String>> map = new HashMap<>();
        for(TagPaperEntity tagPaperEntity: tagEntityList){
            int paperId = tagPaperEntity.getPaperId();
            String tag = tagPaperEntity.getTagName();
            tagPaperDao.deleteByPaperIdAndTagName(paperId, tag);
            TagEntity tagEntity;
            if(tagDao.existsByName(tag)){
                tagEntity = tagDao.findByName(tag);
                tagEntity.setUsedNum(tagEntity.getUsedNum() + 1);
                tagEntity.setLastActiveTime(new Date());

            }else{
                tagEntity = new TagEntity(tag, 1, new Date());
            }
            tagEntity = tagDao.save(tagEntity);
            if(!map.containsKey(paperId)){
                map.put(paperId, new ArrayList<>());
            }
            map.get(paperId).add(tag);
        }
        restTemplate.postForObject(recommendService + initTag, map, void.class);
    }
}

