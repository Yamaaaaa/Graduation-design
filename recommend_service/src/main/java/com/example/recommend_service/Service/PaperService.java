package com.example.recommend_service.Service;

import com.example.recommend_service.Dao.PaperTagRelationDao;
import com.example.recommend_service.Entity.PaperFeatureEntity;
import com.example.recommend_service.Entity.PaperTagRelationEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

    String getTagNameUrl = "getTagNameUrl";

    private Map<Integer, String> getPaperRecommendTag(Integer paper_id){
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
}
