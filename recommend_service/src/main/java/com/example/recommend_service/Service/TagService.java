package com.example.recommend_service.Service;

import com.example.recommend_service.Dao.*;
import com.example.recommend_service.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TagService {
    @Autowired
    PaperTagRelationDao paperTagRelationDao;
    @Autowired
    UserFeatureDao userFeatureDao;
    @Autowired
    TopicTagRelationDao topicTagRelationDao;
    @Autowired
    TopicDao topicDao;
    @Autowired
    PaperFeatureDao paperFeatureDao;
    @Autowired
    TagPaperDao tagPaperDao;
    @Autowired
    SysInfoDao sysInfoDao;
    @Autowired
    RestTemplate restTemplate;

    public void deleteTag(List<Integer> tags){
        for(Integer tag_id: tags) {
            paperTagRelationDao.deleteByTagId(tag_id);
            topicTagRelationDao.deleteByTagId(tag_id);
        }
//        restTemplate.postForObject(deleteTagUrl, tags, void.class);
    }

    public void deleteTag(int tag){
        paperTagRelationDao.deleteByTagId(tag);
        topicTagRelationDao.deleteByTagId(tag);
//        List<Integer> tagList = new ArrayList<>();
//        tagList.add(tag);
//        restTemplate.postForObject(deleteTagUrl, tagList, void.class);
    }

    public void mergeTag(int goalTag, int operateTag){
        List<PaperTagRelationEntity> operateRelationList = paperTagRelationDao.findByTagId(operateTag);
        for(PaperTagRelationEntity paperTagRelationEntity: operateRelationList){
            int paper_id = paperTagRelationEntity.getPaperId();
            PaperTagRelationEntity goalEntity = paperTagRelationDao.findByPaperIdAndTagId(paper_id, goalTag);
            if(goalEntity!=null){
                goalEntity.setDegree(goalEntity.getDegree() + paperTagRelationEntity.getDegree());
            }else{
                paperTagRelationDao.save(new PaperTagRelationEntity(paper_id, goalTag, paperTagRelationEntity.getDegree(), paperTagRelationEntity.getTagNum()));
            }
        }
        deleteTag(operateTag);
    }

    public void editTopicName(int topicId, String name){
        TopicEntity topicEntity= topicDao.findById(topicId);
        topicEntity.setName(name);
        topicDao.save(topicEntity);
    }

    public void refreshTopic(int topicNum){
        topicDao.deleteAll();
        for(int i=0; i<topicNum; i++){
            topicDao.save(new TopicEntity(i));
        }
    }

    public List<TopicTagData> getTopicTagData(){
        List<TopicTagData> topicTagData = new ArrayList<>();
        List<TopicEntity> topicEntities = topicDao.findAll();
        for(TopicEntity topicEntity: topicEntities){
            int topicId = topicEntity.getId();
            Map<Integer, Double> tagRelation = new HashMap<>();
            for(TopicTagRelationEntity topicTagRelationEntity: topicTagRelationDao.findByTopicId(topicId)){
                tagRelation.put(topicTagRelationEntity.getTagId(), topicTagRelationEntity.getDegree());
            }
            topicTagData.add(new TopicTagData(topicId, topicDao.findById(topicId).getName(), tagRelation));
        }
        return topicTagData;
    }

    public List<Integer> getTagRelatePaper(int tagId, int pageNum, int pageSize){
        float paper_tag_th = sysInfoDao.findByName("paper_tag_th").getVal()/10;
        return paperTagRelationDao.getPaperIdByTagIdAndDegree(tagId, paper_tag_th, PageRequest.of(pageNum, pageSize));
    }

    public List<Integer> getUncheckList(){
        return tagPaperDao.getAllPaperId();
    }

    public List<Integer> getUncheckTagId(int paperId){
        return tagPaperDao.findAllByPaperId(paperId);
    }
}
