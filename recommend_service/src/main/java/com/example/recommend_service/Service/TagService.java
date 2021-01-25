package com.example.recommend_service.Service;

import com.example.recommend_service.Dao.PaperTagRelationDao;
import com.example.recommend_service.Dao.TopicTagRelationDao;
import com.example.recommend_service.Dao.UserFeatureDao;
import com.example.recommend_service.Entity.PaperTagRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagService {
    @Autowired
    PaperTagRelationDao paperTagRelationDao;
    @Autowired
    UserFeatureDao userFeatureDao;
    @Autowired
    TopicTagRelationDao topicTagRelationDao;
    @Autowired
    RestTemplate restTemplate;

    String deleteTagUrl = "deleteTagUrl";
    public void deleteTag(List<Integer> tags){
        for(Integer tag_id: tags) {
            paperTagRelationDao.deleteByTagId(tag_id);
            topicTagRelationDao.deleteByTagId(tag_id);
        }
        restTemplate.postForObject(deleteTagUrl, tags, void.class);
    }

    public void deleteTag(int tag){
        paperTagRelationDao.deleteByTagId(tag);
        topicTagRelationDao.deleteByTagId(tag);
        List<Integer> tagList = new ArrayList<>();
        restTemplate.postForObject(deleteTagUrl, tagList, void.class);
    }

    public void mergeTag(int goalTag, int operateTag){
        List<PaperTagRelationEntity> operateRelationList = paperTagRelationDao.findByTagId(operateTag);
        for(PaperTagRelationEntity paperTagRelationEntity: operateRelationList){
            int paper_id = paperTagRelationEntity.getPaperId();
            PaperTagRelationEntity goalEntity = paperTagRelationDao.findByPaperIdAndTagId(paper_id, goalTag);
            if(goalEntity!=null){
                goalEntity.setDegree(goalEntity.getDegree() + paperTagRelationEntity.getDegree());
            }else{
                paperTagRelationDao.save(new PaperTagRelationEntity(paper_id, goalTag, paperTagRelationEntity.getDegree()));
            }
        }
        deleteTag(operateTag);
    }
}
