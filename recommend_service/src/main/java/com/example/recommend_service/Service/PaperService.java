package com.example.recommend_service.Service;

import com.example.recommend_service.Dao.*;
import com.example.recommend_service.Entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Paper;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@Component
public class PaperService {
    @Autowired
    PaperTagRelationDao paperTagRelationDao;
    @Autowired
    PaperInfoDao paperInfoDao;
    @Autowired
    PaperFeatureDao paperFeatureDao;
    @Autowired
    TopicTagRelationDao topicTagRelationDao;
    @Autowired
    SysInfoDao sysInfoDao;
    @Autowired
    TopicDao topicDao;
    @Autowired
    private RestTemplate restTemplate;

    String tagServiceUrl = "http://localhost:50003/";
    String paperServiceUrl = "http://localhost:50001/";
    String getPaperData = "paperData";
    String getTagPaperData = "getTagPaperData";
    String updatePaperTagIndex = "updatePaperTagIndex";

    public List<String> getPaperRecommendTag(Integer paper_id){
        List<String> recommendTagId = new ArrayList<>();
        for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationDao.findTop6ByPaperIdOrderByDegreeDesc(paper_id)){
            recommendTagId.add(paperTagRelationEntity.getTagName());
        }
        System.out.println("paperTagData: " + recommendTagId);
        return recommendTagId;
    }

    public Map<Integer, List<String>> getPaperTagData(List<Integer> paperIdList){
        Map<Integer, List<String>> paperTagData = new HashMap<>();
        for(Integer paperId: paperIdList){
            paperTagData.put(paperId, getPaperRecommendTag(paperId));
        }
        System.out.println("paperTagData: " + paperTagData);
        return paperTagData;
    }

    public void addPaperTag(int paperId, List<String> tags){
        PaperInfoEntity paperInfoEntity = paperInfoDao.findById(paperId);
        if(paperInfoEntity==null){
            paperInfoEntity = paperInfoDao.save(new PaperInfoEntity(paperId, 0, 0));
        }
        for(String tagId: tags){
            int tagNum = paperInfoEntity.getTaggedNum();
            if(paperTagRelationDao.existsByPaperIdAndTagName(paperId, tagId)) {
                PaperTagRelationEntity paperTagRelationEntity = paperTagRelationDao.findByPaperIdAndTagName(paperId, tagId);
                paperTagRelationEntity.setTagNum(paperTagRelationEntity.getTagNum() + 1);
                paperTagRelationDao.save(paperTagRelationEntity);
            }else{
                paperTagRelationDao.save(new PaperTagRelationEntity(paperId, tagId));
            }
        }
        paperInfoEntity.setTaggedNum(paperInfoEntity.getTaggedNum()+1);
        paperInfoEntity.setUncheckNum(paperInfoEntity.getUncheckNum()+1);
        paperInfoDao.save(paperInfoEntity);
    }

    //更新论文与标签的相关性数据
    public void updatePaperTag(){
        Set<Integer> needUpdateIndexPaper = new HashSet<>();
        double paper_tag_th = (double)sysInfoDao.findByName("paper_tag_th").getVal()/10;

        for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationDao.findAllByRenew(false)){
            int tagNum = paperInfoDao.findById(paperTagRelationEntity.getPaperId()).getTaggedNum();
            float degree = (float)paperTagRelationEntity.getTagNum()/tagNum;
            Float oldDegree = paperTagRelationEntity.getDegree();
            if(oldDegree == null){
                oldDegree = (float)0;
            }
            paperTagRelationEntity.setDegree(degree);
            if(0>(oldDegree-paper_tag_th)*(degree-paper_tag_th)){
                needUpdateIndexPaper.add(paperTagRelationEntity.getPaperId());
            }
            paperTagRelationEntity.setRenew(true);
            paperTagRelationDao.save(paperTagRelationEntity);
        }

        //更新论文索引
//        List<Map<String, String>> papersNewTagIndex = new ArrayList<>();
//        for(Integer paperId: needUpdateIndexPaper){
//            Map<String, String> paperNewTagIndex = new HashMap<>();
//            paperNewTagIndex.put("id", "" + paperId);
//            List<String> tagIdList = paperTagRelationDao.findTagNameByPaperIdAndDegreeGreaterThanEqual(paperId, (float)paper_tag_th);
//            System.out.println("tagNameList:"+tagIdList);
//            paperNewTagIndex.put("tags", sTagName);
//            papersNewTagIndex.add(paperNewTagIndex);
//        }
//        restTemplate.postForObject(updatePaperTagIndex, papersNewTagIndex, void.class);
    }

    public void IdaTopicCluster(int nTopics){
        List<String> tagIdList = paperTagRelationDao.findAllTagName();
        List<Integer> paperIdList = paperTagRelationDao.findAllPaperId();

        String baseCommand = "python ldaCluster.py";
        String commandStr = nTopics + " " + tagIdList.size() + " " + paperIdList.size();

        for (Integer paperId : paperIdList) {
            for(String tagName: tagIdList) {
                if(paperTagRelationDao.existsByPaperIdAndTagName(paperId, tagName)){
                    commandStr += " " + paperTagRelationDao.findByPaperIdAndTagName(paperId, tagName).getTagNum();
                }else{
                    commandStr += " " + 0;
                }
            }
        }


        String filePath = "tagCountData.txt";
        try{
            File file = new File(filePath);
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(commandStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Process pr;
        String line = null;

        try {
            pr = Runtime.getRuntime().exec(baseCommand);
            InputStreamReader in =new InputStreamReader(pr.getInputStream());
//		BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            LineNumberReader input = new LineNumberReader(in);
            for(int i=0; i<paperIdList.size(); ++i) {
                line = input.readLine();
                System.out.println("line："+line);
                line = line.substring(2, line.length()-1);
                if(i == paperIdList.size()-1){
                    line = line.substring(0, line.length()-1);
                }
                String[] groupNums = line.trim().split("\\s+");
                System.out.println("计算结果："+groupNums[0] + "  " + groupNums[1] + "  " + groupNums[2] + "  ");
                for(int j=0; j<nTopics; ++j){
                    paperFeatureDao.save(new PaperFeatureEntity(paperIdList.get(i), j, Float.parseFloat(groupNums[j])));
                }
            }
            int loop = nTopics *(tagIdList.size()/6);
            int endNum = tagIdList.size()/6;
            if(tagIdList.size()%6 != 0){
                loop += nTopics;
            }
            System.out.println("loop:" + loop);
            int tagIndex = 0;
            int topicId = 0;
            for(int i=0; i<loop; ++i){
                line = input.readLine();
                System.out.println("i = " + i + "   " + "line："+line);
                if(i%(endNum+1)==0){
                    line = line.substring(2, line.length());
                }else if(i == loop - 1){
                    line = line.substring(0, line.length()-2);
                }else if((i + 1)%(endNum + 1) == 0){
                    line = line.substring(0, line.length()-1);
                }
                System.out.println("计算结果："+line);
                String[] groupNums = line.trim().split("\\s+");
                int loop2 = 6;
                if(tagIdList.size() < 6){
                    loop2 = tagIdList.size();
                }
                for(int j=0; j<loop2; ++j){
                    if(j >= groupNums.length){
                        break;
                    }
                    System.out.println("tagIndex："+tagIndex);
                    topicTagRelationDao.save(new TopicTagRelationEntity(topicId, tagIdList.get(tagIndex), Float.parseFloat(groupNums[j])));
                    ++tagIndex;
                    if(tagIndex >= tagIdList.size()) {
                        topicId++;
                        tagIndex -= tagIdList.size();
                    }
                }
            }
            input.close();
            in.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public List<PaperInfoEntity> getTagPaperData(){
        PaperInfoEntity totalPaperInfoEntity = new PaperInfoEntity(-1, paperInfoDao.getTotalTagNum(), paperInfoDao.getNewTagNum());
        List<PaperInfoEntity> tagPaperData = paperInfoDao.findTop50ByOrderByUncheckNumDesc();
        tagPaperData.add(0, totalPaperInfoEntity);
        return tagPaperData;
    }

    public List<PaperFeatureData> getPaperFeatureData(int paperId){
        List<PaperFeatureData> paperFeatureData = new ArrayList<>();
        float paper_tag_th = sysInfoDao.findByName("paper_tag_th").getVal()/10;

        Map<String, Float> paperTagRelation = new HashMap<>();
        List<PaperTagRelationEntity> paperTagRelationEntities = paperTagRelationDao.findByPaperIdAndDegreeGreaterThanEqual(paperId, paper_tag_th);
        for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationEntities){
            paperTagRelation.put(paperTagRelationEntity.getTagName(), paperTagRelationEntity.getDegree());
        }

        for(PaperFeatureEntity paperFeatureEntity: paperFeatureDao.findByPaperIdAndDegreeGreaterThanEqual(paperId, paper_tag_th)){
            int topicId = paperFeatureEntity.getTopicId();
            Float topicDegree = paperFeatureEntity.getDegree();
            String topicName = topicDao.findById(topicId).getName();
            Map<String, Float> topicTag = new HashMap<>();
            for(Map.Entry<String, Float> entry: paperTagRelation.entrySet()){
                if(topicTagRelationDao.existsByTopicIdAndTagNameAndDegreeGreaterThanEqual(topicId, entry.getKey(), paper_tag_th)){
                    topicTag.put(entry.getKey(), entry.getValue());
                }
            }
            paperFeatureData.add(new PaperFeatureData(topicId, topicName, topicDegree, topicTag));
        }
        return paperFeatureData;
    }

    public PaperTopicRankData getPaperTopicRankData(int paperId){
        PaperTopicRankData paperTopicRankData = new PaperTopicRankData();
        List<String> topics = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<PaperFeatureEntity> paperFeatureEntities = paperFeatureDao.findAllByPaperId(paperId);
        for(PaperFeatureEntity paperFeatureEntity: paperFeatureEntities){
            topics.add(topicDao.findById(paperFeatureEntity.getTopicId()).getName());
            values.add(paperFeatureEntity.getDegree());
        }
        paperTopicRankData.setTopicsName(topics);
        paperTopicRankData.setTopicsRelate(values);
        return paperTopicRankData;
    }

    public void initTagPaper(Map<Integer, List<String>> tagPaperData){
        for(Map.Entry<Integer, List<String>> tagPaper: tagPaperData.entrySet()){
            addPaperTag(tagPaper.getKey(), tagPaper.getValue());
        }
    }

    public PaperReviewData getManagePaperInfo(int paperId){
        PaperReviewData paperReviewData = new PaperReviewData();
        List<Integer> paperIdList = new ArrayList<>();
        Gson gson = new Gson();
        paperIdList.add(paperId);
        List<PaperEntity> paperEntityList = new ArrayList<>();
        Type type1 = new TypeToken<List<PaperEntity>>(){}.getType();
        String res1 = restTemplate.postForObject(paperServiceUrl + getPaperData, paperIdList, String.class);
        paperEntityList = gson.fromJson(res1, type1);
        PaperEntity paperEntity = paperEntityList.get(0);
        paperReviewData.setPaperId(paperEntity.getId());
        paperReviewData.setPaperTitle(paperEntity.getTitle());
        paperReviewData.setPaperAbstract(paperEntity.getAbst());

        paperReviewData.setPaperTopicRankData(getPaperTopicRankData(paperEntity.getId()));
        paperReviewData.setPaperTagRankData(getPaperTagRankData(paperEntity.getId()));
        paperReviewData.setTopicTagRelateData(getTopicTagRelateData(paperReviewData.getPaperTopicRankData().getTopicsName(), paperReviewData.getPaperTagRankData().getTopicsName()));
        return paperReviewData;
    }

    public List<PaperReviewData> getPaperReviewData(){
        Gson gson = new Gson();
        List<TagPaperEntity> tagPaperEntities = new ArrayList<>();
        Type type = new TypeToken<List<TagPaperEntity>>(){}.getType();
        String res = restTemplate.getForObject(tagServiceUrl + getTagPaperData, String.class);
        tagPaperEntities = gson.fromJson(res, type);

        List<PaperReviewData> paperReviewDataList = new ArrayList<>();
        Set<Integer> paperIdList = new HashSet<>();
        Map<Integer, List<TagName>> paperUncheckTag = new HashMap<>();
        for(TagPaperEntity tagPaperEntity: tagPaperEntities){
            int paperId = tagPaperEntity.getPaperId();
            paperIdList.add(paperId);
            if(!paperUncheckTag.containsKey(paperId)){
                paperUncheckTag.put(paperId, new ArrayList<>());
            }
            TagName tagName = new TagName();
            tagName.setTagName(tagPaperEntity.getTagName());
            paperUncheckTag.get(paperId).add(tagName);
        }

        List<PaperEntity> paperEntityList = new ArrayList<>();
        Type type1 = new TypeToken<List<PaperEntity>>(){}.getType();
        String res1 = restTemplate.postForObject(paperServiceUrl + getPaperData, paperIdList, String.class);
        paperEntityList = gson.fromJson(res1, type1);
        for(PaperEntity paperEntity: paperEntityList){
            PaperReviewData paperReviewData = new PaperReviewData();
            paperReviewData.setPaperId(paperEntity.getId());
            paperReviewData.setPaperTitle(paperEntity.getTitle());
            paperReviewData.setPaperAbstract(paperEntity.getAbst());

            paperReviewData.setUncheckTag(paperUncheckTag.get(paperEntity.getId()));

            paperReviewData.setPaperTopicRankData(getPaperTopicRankData(paperEntity.getId()));
            paperReviewData.setPaperTagRankData(getPaperTagRankData(paperEntity.getId()));
            paperReviewData.setTopicTagRelateData(getTopicTagRelateData(paperReviewData.getPaperTopicRankData().getTopicsName(), paperReviewData.getPaperTagRankData().getTopicsName()));
            paperReviewDataList.add(paperReviewData);
        }
        return paperReviewDataList;
    }

    public PaperTopicRankData getPaperTagRankData(int paperId){
        PaperTopicRankData paperTagRankData = new PaperTopicRankData();
        List<PaperTagRelationEntity> paperTagRelationEntities = paperTagRelationDao.findAllByPaperId(paperId);
        List<String> tags = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationEntities){
            tags.add(paperTagRelationEntity.getTagName());
            values.add(paperTagRelationEntity.getDegree());
        }
        paperTagRankData.setTopicsName(tags);
        paperTagRankData.setTopicsRelate(values);
        return paperTagRankData;
    }

    public TopicTagRelateData getTopicTagRelateData(List<String> topics, List<String> tags){
        TopicTagRelateData topicTagRelateData = new TopicTagRelateData();
        topicTagRelateData.setTopics(topics);
        topicTagRelateData.setTags(tags);
        List<List<Float>> values = new ArrayList<>();
        for(String tag: tags){
            List<Float> temp = new ArrayList<>();
            for(String topic: topics){
                int topicId = topicDao.findByName(topic).getId();
                temp.add(topicTagRelationDao.findByTopicIdAndTagName(topicId, tag).getDegree());
            }
            values.add(temp);
        }
        topicTagRelateData.setValues(values);
        return topicTagRelateData;
    }
}
