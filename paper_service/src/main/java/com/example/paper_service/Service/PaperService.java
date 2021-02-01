package com.example.paper_service.Service;

import com.example.paper_service.Dao.PaperDao;
import com.example.paper_service.Dao.PaperHotDao;
import com.example.paper_service.Dao.PaperSimpleDao;
import com.example.paper_service.Dao.SysInfoDao;
import com.example.paper_service.Entity.*;
import com.example.paper_service.Util.HttpJob;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Paper;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaperService {
    @Autowired
    PaperDao paperDao;
    @Autowired
    PaperHotDao paperHotDao;
    @Autowired
    SysInfoDao sysInfoDao;
    @Autowired
    PaperSimpleDao paperSimpleDao;
    @Autowired
    private RestTemplate restTemplate;

    String getPaperRecommendData = "getPaperRecommendData";

    public List<PaperSimpleEntity> getPaperSimpleDataList(List<Integer> paperIDList){
        return paperSimpleDao.findByIdList(paperIDList);
    }

    public String getPaperData(int paper_id){
        PaperEntity paperEntity = paperDao.findById(paper_id);
        return paperEntity.getAbst();
    }

    public List<PaperItemData> getHotPaperList(){
        List<PaperEntity> paperEntityList = paperDao.findTop20ByOrderByRecentBrowseNumDesc();
        return getPaperListData(paperEntityList);
    }

    public List<PaperItemData> getPaperItemData(List<Integer> paperIdList){
        List<PaperItemData> paperListData = new ArrayList<>();
        for(Integer paperId: paperIdList){
            Map<String, Object> map = new HashMap<>();
            map.put("paper_id", paperId);
            String url = HttpJob.generateRequestParameters("http", getPaperRecommendData, map);
            PaperRecommendData paperRecommendData = restTemplate.getForObject(url, PaperRecommendData.class);
            assert paperRecommendData != null;
            paperListData.add(new PaperItemData(paperDao.findById((int)paperId), paperRecommendData));
        }
        return paperListData;
    }

    public List<PaperItemData> getPaperListData(List<PaperEntity> paperEntityList){
        List<PaperItemData> paperListData = new ArrayList<>();
        for(PaperEntity paperEntity: paperEntityList){
            Map<String, Object> map = new HashMap<>();
            map.put("paper_id", paperEntity.getId());
            String url = HttpJob.generateRequestParameters("http", getPaperRecommendData, map);
            PaperRecommendData paperRecommendData = restTemplate.getForObject(url, PaperRecommendData.class);
            assert paperRecommendData != null;
            paperListData.add(new PaperItemData(paperEntity, paperRecommendData));
        }
        return paperListData;
    }

    public List<PaperItemData> getPaperPage(int pageNum, int pageSize){
        List<PaperEntity> paperEntityList = paperDao.findAll(PageRequest.of(pageNum, pageSize)).getContent();
        return getPaperListData(paperEntityList);
    }

    public List<PaperEntity> getManagePaperPage(int pageNum, int pageSize){
        return paperDao.findAll(PageRequest.of(pageNum, pageSize)).getContent();
    }


    public void increaseBrowseNum(int paperID){
        PaperEntity paperEntity = paperDao.findById(paperID);
        paperEntity.setBrowseNum(paperEntity.getRecentBrowseNum()+1);
        paperDao.save(paperEntity);

        PaperHotEntity paperHotEntity = paperHotDao.findByPaperIdAndSerNum(paperID, sysInfoDao.findByName("current_ser_num").getVal());
        paperHotEntity.setBrowseNum(paperHotEntity.getBrowseNum()+1);
        paperHotDao.save(paperHotEntity);
    }

    public  void updatePaperRecentBrowseNum(){
        List<Integer> IdList = paperDao.findAllId();
        SysInfoEntity sysInfoEntity = sysInfoDao.findByName("current_ser_num");
        int currentSerNum = sysInfoEntity.getVal();
        int paperHotTW = sysInfoDao.findByName("paper_hot_tw").getVal();
        int newSerNum = (currentSerNum+1)%paperHotTW;

        for(Integer id :IdList){
            PaperEntity paperEntity = paperDao.findById((int)id);
            paperEntity.setRecentBrowseNum(paperHotDao.getSumBrowseNumByPaperId(id));

            PaperHotEntity paperHotEntity = paperHotDao.findByPaperIdAndSerNum(id, newSerNum);
            paperHotEntity.setBrowseNum(0);
            paperHotDao.save(paperHotEntity);
            sysInfoEntity.setVal(newSerNum);
            sysInfoDao.save(sysInfoEntity);
            paperDao.save(paperEntity);
        }
    }
}
