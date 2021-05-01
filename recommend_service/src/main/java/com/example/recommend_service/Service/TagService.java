package com.example.recommend_service.Service;

import com.example.recommend_service.Dao.*;
import com.example.recommend_service.Entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

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
    SysInfoDao sysInfoDao;
    @Autowired
    PaperInfoDao paperInfoDao;
    @Autowired
    RestTemplate restTemplate;

    String paperServiceUrl = "http://localhost:50001/";
    String tagServiceUrl = "http://localhost:50003/";
    String getSameTag = "sameTagData";
    String getPaperSimpleData = "paperSimpleData";
    String searchPaper = "searchPaperForManager";

    @Transactional
    public void deleteTag(List<String> tags){
        for(String tag_id: tags) {
            paperTagRelationDao.deleteByTagName(tag_id);
            topicTagRelationDao.deleteByTagName(tag_id);
        }
//        restTemplate.postForObject(deleteTagUrl, tags, void.class);
    }

    @Transactional
    public void deleteTag(String tag){
        paperTagRelationDao.deleteByTagName(tag);
        topicTagRelationDao.deleteByTagName(tag);
//        List<Integer> tagList = new ArrayList<>();
//        tagList.add(tag);
//        restTemplate.postForObject(deleteTagUrl, tagList, void.class);
    }

    public void mergeTag(String goalTag, List<String> operateTags){
        for(String operateTag: operateTags) {
            List<PaperTagRelationEntity> operateRelationList = paperTagRelationDao.findByTagName(operateTag);
            for (PaperTagRelationEntity paperTagRelationEntity : operateRelationList) {
                int paper_id = paperTagRelationEntity.getPaperId();
                PaperTagRelationEntity goalEntity = paperTagRelationDao.findByPaperIdAndTagName(paper_id, goalTag);
                if (goalEntity != null) {
                    goalEntity.setDegree(goalEntity.getDegree() + paperTagRelationEntity.getDegree());
                } else {
                    paperTagRelationDao.save(new PaperTagRelationEntity(paper_id, goalTag, paperTagRelationEntity.getDegree(), paperTagRelationEntity.getTagNum()));
                }
            }
        }
        deleteTag(operateTags);
    }

    public void editTopicName(int topicId, String name){
        TopicEntity topicEntity= topicDao.findById(topicId);
        topicEntity.setName(name);
        topicDao.save(topicEntity);
    }

    public void deleteTopicData(){
        topicTagRelationDao.deleteAll();
        paperFeatureDao.deleteAll();
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
            List<String> tag = new ArrayList<>();
            List<Float> value = new ArrayList<>();
            for(TopicTagRelationEntity topicTagRelationEntity: topicTagRelationDao.findByTopicId(topicId)){
                tag.add(topicTagRelationEntity.getTagName());
                value.add(topicTagRelationEntity.getDegree());
            }
            topicTagData.add(new TopicTagData(topicId, topicDao.findById(topicId).getName(), tag, value));
        }
        return topicTagData;
    }

    //获取主题管理界面相关信息
    public List<TopicPaperData> getTopicPaperData(){
        List<TopicPaperData> topicsPaperData = new ArrayList<>();
        Map<Integer, String> topicMap = new HashMap<>();
        List<TopicEntity> topicEntities = topicDao.findAll();
        for(TopicEntity topicEntity: topicEntities) {
            topicMap.put(topicEntity.getId(), topicEntity.getName());
        }

        for(TopicEntity topicEntity: topicEntities){
            TopicPaperData topicPaperData = new TopicPaperData();
            topicPaperData.setTopicId(topicEntity.getId());
            topicPaperData.setTopicName(topicEntity.getName());

            List<PaperData> papersData = new ArrayList<>();
            List<PaperFeatureEntity> relatePaperList = paperFeatureDao.findByTopicIdAndDegreeGreaterThan(topicEntity.getId(), (float)0.3);
            List<Integer> paperIdList = new ArrayList<>();
            Set<String> topicSet = new HashSet<>();
            Set<String> tagSet = new HashSet<>();
            for(PaperFeatureEntity paperFeatureEntity: relatePaperList){
                paperIdList.add(paperFeatureEntity.getPaperId());
                PaperData paperData = new PaperData();
                paperData.setPaperId(paperFeatureEntity.getPaperId());
                paperData.setRelateValue((paperFeatureEntity.getDegree()));

                List<TopicSimpleData> paperTopics = getPaperTopicSimpleData(paperFeatureEntity.getPaperId(), topicMap, topicSet);
                paperData.setPaperTopics(paperTopics);

                List<PaperTagData> paperTags = getPaperTagData(paperFeatureEntity.getPaperId(), tagSet);
                paperData.setPaperTags(paperTags);
                papersData.add(paperData);
            }

            List<PaperSimpleEntity> paperSimpleEntities = new ArrayList<>();
            String url = paperServiceUrl + getPaperSimpleData;
            String res = restTemplate.postForObject(url, paperIdList, String.class);
            Gson gson = new Gson();
            Type type = new TypeToken<List<PaperSimpleEntity>>(){}.getType();
            paperSimpleEntities = gson.fromJson(res, type);
            assert papersData.size() == paperSimpleEntities.size();
            for(int i=0; i<paperSimpleEntities.size(); ++i){
                papersData.get(i).setPaperTitle(paperSimpleEntities.get(i).getTitle());
                papersData.get(i).setPaperBrowse(paperSimpleEntities.get(i).getBrowseNum());
                papersData.get(i).setPaperHot(paperSimpleEntities.get(i).getRecentBrowseNum());
            }

            topicPaperData.setPaperData(papersData);
            topicPaperData.setTopicSet(SetItem.getSetItemList(topicSet));
            topicPaperData.setTagSet(SetItem.getSetItemList(tagSet));

            topicsPaperData.add(topicPaperData);
        }
        return topicsPaperData;
    }

    //获取管理界面论文信息
    public PaperManageData getPaperData(List<Integer> paperIdList){
        List<PaperManageItemData> paperManageItemData = new ArrayList<>();

        Map<Integer, String> topicMap = new HashMap<>();
        List<TopicEntity> topicEntities = topicDao.findAll();
        for(TopicEntity topicEntity: topicEntities) {
            topicMap.put(topicEntity.getId(), topicEntity.getName());
        }

        Set<String> topicSet = new HashSet<>();
        Set<String> tagSet = new HashSet<>();

        for(Integer paperId: paperIdList){
            PaperManageItemData paperData = new PaperManageItemData();
            paperData.setPaperId(paperId);

            List<TopicSimpleData> paperTopics = getPaperTopicSimpleData(paperId, topicMap, topicSet);
            paperData.setPaperTopics(paperTopics);

            List<PaperTagData> paperTags = getPaperTagData(paperId, tagSet);
            paperData.setPaperTags(paperTags);

            PaperInfoEntity paperInfoEntity = paperInfoDao.findById((int)paperId);

            paperData.setAddTagNum(paperInfoEntity.getTaggedNum());
            paperManageItemData.add(paperData);
        }

        List<PaperSimpleEntity> paperSimpleEntities = new ArrayList<>();
        String url = paperServiceUrl + getPaperSimpleData;
        String res = restTemplate.postForObject(url, paperIdList, String.class);
        System.out.println("paperIdList: " + paperIdList);
        System.out.println("paperSimpleData: " + res);
        Gson gson = new Gson();
        Type type = new TypeToken<List<PaperSimpleEntity>>(){}.getType();
        paperSimpleEntities = gson.fromJson(res, type);

        for(int i=0; i<paperSimpleEntities.size(); ++i){
            paperManageItemData.get(i).setPaperTitle(paperSimpleEntities.get(i).getTitle());
            paperManageItemData.get(i).setPaperBrowse(paperSimpleEntities.get(i).getBrowseNum());
            paperManageItemData.get(i).setPaperHot(paperSimpleEntities.get(i).getRecentBrowseNum());
        }
        PaperManageData paperManageData = new PaperManageData();
        paperManageData.setPaperManageItemData(paperManageItemData);
        paperManageData.setTagSet(SetItem.getSetItemList(tagSet));
        paperManageData.setTopicSet(SetItem.getSetItemList(topicSet));
        return paperManageData;
    }


    //获取论文管理界面的论文信息
    public PaperManageData getPaperManageData(){
        List<Integer> paperIdList = paperFeatureDao.findAllPaperId();
        return getPaperData(paperIdList);
    }

    private List<TopicSimpleData> getPaperTopicSimpleData(int paperId, Map<Integer, String> topicMap, Set<String> topicSet){
        List<TopicSimpleData> paperTopics = new ArrayList<>();
        List<PaperFeatureEntity> paperTopicData = paperFeatureDao.findByPaperIdAndDegreeGreaterThanEqual(paperId, (float)0.3);
        for(PaperFeatureEntity topicData: paperTopicData){
            TopicSimpleData topicSimpleData = new TopicSimpleData();
            topicSet.add(topicMap.get(topicData.getTopicId()));
            topicSimpleData.setPaperTopicID(topicData.getTopicId());
            topicSimpleData.setPaperTopicName(topicMap.get(topicData.getTopicId()));
            paperTopics.add(topicSimpleData);
        }
        return paperTopics;
    }

    public List<PaperTagData> getPaperTagData(int paperId, Set<String> tagSet){
        List<PaperTagData> paperTags = new ArrayList<>();
        List<PaperTagRelationEntity> paperTagRelationEntities = paperTagRelationDao.findTop6ByPaperIdOrderByDegreeDesc(paperId);
        for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationEntities){
            PaperTagData paperTagData = new PaperTagData();
            tagSet.add(paperTagRelationEntity.getTagName());
            paperTagData.setTagName(paperTagRelationEntity.getTagName());
            //System.out.println("tagName = " + paperTagRelationEntity.getTagName());
            int topicId = topicTagRelationDao.findTopByTagNameOrderByDegreeDesc(paperTagRelationEntity.getTagName()).getTopicId();
            //System.out.println("topicId = " + topicId);
            paperTagData.setTopicID(topicId);
            paperTags.add(paperTagData);
        }
        return paperTags;
    }
    //获取标签详细信息界面
    public TagManageData getTagManageData(String tag){
        TagManageData tagManageData = new TagManageData();
        tagManageData.setPaperManageData(getTagRelatePaper(tag));
        tagManageData.setTagUsedData(getTagUsedData(tag));
        String temp = restTemplate.getForObject(tagServiceUrl+getSameTag+"?tagId="+tag, String.class);
        Type type = new TypeToken<List<String>>(){}.getType();
        Gson gson = new Gson();
        List<String> sameTag = gson.fromJson(temp, type);
        tagManageData.setSameTag(sameTag);
        return tagManageData;
    }

    //获取标签相关论文信息
    public PaperManageData getTagRelatePaper(String tagId){
        List<PaperTagRelationEntity> paperTagRelationEntities = paperTagRelationDao.findAllByTagNameAndDegreeGreaterThan(tagId, (float)0.3);
        List<Integer> paperIdList = new ArrayList<>();
        for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationEntities){
            paperIdList.add(paperTagRelationEntity.getPaperId());
        }
        return getPaperData(paperIdList);
    }


    public TagUsedData getTagUsedData(String tagId){
        TagUsedData tagUsedData = new TagUsedData();
        List<String> lastCalender = new ArrayList<>();
        List<Integer> lastUsedNum = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
        Random random = new Random();
        for(int i=0; i<7; ++i){
            lastCalender.add(sdf.format(date));
            lastUsedNum.add(random.nextInt(10));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            date = calendar.getTime();
        }
        tagUsedData.setLastCalender(lastCalender);
        tagUsedData.setLastUsedNum(lastUsedNum);
        return tagUsedData;
    }

    public List<String> getTopics(){
        List<String> topics = new ArrayList<>();
        for(TopicEntity topicEntity :topicDao.findAll()){
            topics.add(topicEntity.getName());
        }
        return topics;
    }

    public PaperManageData searchPaper(String text){
        Map<Integer, String> topicMap = new HashMap<>();
        List<TopicEntity> topicEntities = topicDao.findAll();
        for(TopicEntity topicEntity: topicEntities) {
            topicMap.put(topicEntity.getId(), topicEntity.getName());
        }

        List<PaperSimpleEntity> searchResult = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<PaperSimpleEntity>>(){}.getType();
        String res = restTemplate.postForObject(paperServiceUrl + searchPaper, text, String.class);
        searchResult = gson.fromJson(res, type);

        List<PaperManageItemData> paperManageItemData = new ArrayList<>();
        Set<String> topicSet = new HashSet<>();
        Set<String> tagSet = new HashSet<>();
        for(PaperSimpleEntity paperSimpleEntity: searchResult){
            int paperId = paperSimpleEntity.getId();
            PaperManageItemData paperData = new PaperManageItemData();
            paperData.setPaperId(paperSimpleEntity.getId());

            List<TopicSimpleData> paperTopics = getPaperTopicSimpleData(paperSimpleEntity.getId(), topicMap, topicSet);
            paperData.setPaperTopics(paperTopics);

            List<PaperTagData> paperTags = getPaperTagData(paperId, tagSet);
            paperData.setPaperTags(paperTags);

            PaperInfoEntity paperInfoEntity = paperInfoDao.findById(paperId);

            paperData.setAddTagNum(paperInfoEntity.getTaggedNum());

            paperData.setPaperTitle(paperSimpleEntity.getTitle());
            paperData.setPaperBrowse(paperSimpleEntity.getBrowseNum());
            paperData.setPaperHot(paperSimpleEntity.getRecentBrowseNum());
            paperManageItemData.add(paperData);
        }
        PaperManageData paperManageData = new PaperManageData();
        paperManageData.setPaperManageItemData(paperManageItemData);
        paperManageData.setTagSet(SetItem.getSetItemList(tagSet));
        paperManageData.setTopicSet(SetItem.getSetItemList(topicSet));
        return paperManageData;
    }
}
