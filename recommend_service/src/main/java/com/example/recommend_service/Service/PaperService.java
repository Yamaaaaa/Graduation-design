package com.example.recommend_service.Service;

import com.example.recommend_service.Dao.*;
import com.example.recommend_service.Entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    String getTagNameUrl = "getTagNameUrl";

    public Map<Integer, String> getPaperRecommendTag(Integer paper_id){
        List<Integer> recommendTagId = new ArrayList<>();
        for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationDao.findTop6ByPaperIdOrderByDegreeDesc(paper_id)){
            recommendTagId.add(paperTagRelationEntity.getTagId());
        }
        String sTagName = restTemplate.postForObject(getTagNameUrl, recommendTagId, String.class);
        System.out.println("tagNameList:"+recommendTagId);
        Type mapType = new TypeToken<Map<Integer,String>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(sTagName,mapType);
    }

    public void addPaperTag(int paperId, List<Integer> tags){
        PaperInfoEntity paperInfoEntity = paperInfoDao.findById(paperId);

        for(Integer tagId: tags){
            int tagNum = paperInfoEntity.getTaggedNum();
            if(paperTagRelationDao.existsByPaperIdAndTagId(paperId, tagId)) {
                PaperTagRelationEntity paperTagRelationEntity = paperTagRelationDao.findByPaperIdAndTagId(paperId, tagId);
                paperTagRelationEntity.setDegree((double) ((paperTagRelationEntity.getTagNum() + 1)/tagNum));
                paperTagRelationEntity.setTagNum(paperTagRelationEntity.getTagNum() + 1);
                paperTagRelationDao.save(paperTagRelationEntity);
            }else{
                paperTagRelationDao.save(new PaperTagRelationEntity(paperId, tagId, (double) (1/tagNum), 1));
            }
        }

        paperInfoEntity.setUncheckNum(paperInfoEntity.getUncheckNum()+1);
        paperInfoDao.save(paperInfoEntity);
    }

    public void IdaTopicCluster(int nTopics){
        List<Integer> tagIdList = paperTagRelationDao.findAllTagId();
        List<Integer> paperIdList = paperTagRelationDao.findAllPaperId();

        String baseCommand = "python ldaCluster.py ";
        String commandStr = nTopics + " " + tagIdList.size() + " " + paperIdList.size();

        for (Integer paperId : paperIdList) {
            for(Integer tagId: tagIdList) {
                if(paperTagRelationDao.existsByPaperIdAndTagId(paperId, tagId)){
                    commandStr += " " + paperTagRelationDao.findByPaperIdAndTagId(paperId, tagId).getTagNum();
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
            line = input.readLine();
            input.close();
            in.close();
            System.out.println("计算结果："+line);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String[] groupNums = line.split(" ");
        int count = 0;
        for(int i=0; i<paperIdList.size(); ++i){
            for(int j=0; j<nTopics; ++j){
                paperFeatureDao.save(new PaperFeatureEntity(paperIdList.get(i), j, Double.parseDouble(groupNums[count++])));
            }
        }
        for(int i=0; i<nTopics; ++i){
            for(int j=0; j<tagIdList.size(); ++j){
                topicTagRelationDao.save(new TopicTagRelationEntity(i, tagIdList.get(j), Double.parseDouble(groupNums[count++])));
            }
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

        Map<Integer, Double> paperTagRelation = new HashMap<>();
        List<PaperTagRelationEntity> paperTagRelationEntities = paperTagRelationDao.findByPaperIdAndDegreeGreaterThanEqual(paperId, paper_tag_th);
        for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationEntities){
            paperTagRelation.put(paperTagRelationEntity.getTagId(), paperTagRelationEntity.getDegree());
        }

        for(PaperFeatureEntity paperFeatureEntity: paperFeatureDao.findByPaperIdAndDegreeGreaterThanEqual(paperId, paper_tag_th)){
            int topicId = paperFeatureEntity.getTopicId();
            Double topicDegree = paperFeatureEntity.getDegree();
            String topicName = topicDao.findById(topicId).getName();
            Map<Integer, Double> topicTag = new HashMap<>();
            for(Map.Entry<Integer, Double> entry: paperTagRelation.entrySet()){
                if(topicTagRelationDao.existsByTopicIdAndTagIdAndDegreeGreaterThanEqual(topicId, entry.getKey(), paper_tag_th)){
                    topicTag.put(entry.getKey(), entry.getValue());
                }
            }
            paperFeatureData.add(new PaperFeatureData(topicId, topicName, topicDegree, topicTag));
        }
        return paperFeatureData;
    }
}
