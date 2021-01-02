package com.example.paper_service.Service;

import com.example.paper_service.Dao.PaperDao;
import com.example.paper_service.Dao.PaperHotDao;
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
    private RestTemplate restTemplate;

    String getPaperRecommendData = "getPaperRecommendData";

    public List<PaperSimpleData> getPaperSimpleDataList(List<Integer> paperIDList){
        List<PaperSimpleData> paperSimpleDataList = new ArrayList<>();
        for(Integer paperID : paperIDList) {
            paperSimpleDataList.add(new PaperSimpleData(paperDao.findById((int)paperID)));
        }
        return paperSimpleDataList;
    }

    public String getPaperData(int paper_id){
        PaperEntity paperEntity = paperDao.findById(paper_id);
        return paperEntity.getAbst();
    }

    public List<PaperItemData> getHotPaperList(){
        List<PaperEntity> paperEntityList = paperDao.findTop20ByOrderByRecentBrowseNumDesc();
        return getPaperListData(paperEntityList);
    }

    List<PaperItemData> getPaperListData(List<PaperEntity> paperEntityList){
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

    public void increaseBrowseNum(int paperID){
        PaperEntity paperEntity = paperDao.findById(paperID);
        paperEntity.setBrowseNum(paperEntity.getRecentBrowseNum()+1);
        paperEntity.setRenew(false);
        paperDao.save(paperEntity);

        PaperHotEntity paperHotEntity = paperHotDao.findByPaperIdAndSerNum(paperID, paperEntity.getCurrentSerial());
        paperHotEntity.setBrowseNum(paperHotEntity.getBrowseNum()+1);
        paperHotDao.save(paperHotEntity);
    }

    public  void updatePaperRecentBrowseNum(){
        List<Integer> IdList = paperDao.findAllId();
        for(Integer id :IdList){
            PaperEntity paperEntity = paperDao.findById((int)id);
            if(paperEntity.getRenew())
                return ;
            int paperHotTimeWindow = sysInfoDao.findByName("paper_hot_tw").getVal();
            int currentSerialNum = paperEntity.getCurrentSerial();
            int freeSerialNum = (currentSerialNum+paperHotTimeWindow)%(paperHotTimeWindow+1);
            int recentBrowseNum = paperEntity.getRecentBrowseNum();
            int increase = paperHotDao.findByPaperIdAndSerNum(id, currentSerialNum).getBrowseNum();
            int decrease = paperHotDao.findByPaperIdAndSerNum(id, freeSerialNum).getBrowseNum();
            recentBrowseNum = recentBrowseNum + increase - decrease;
            paperEntity.setRecentBrowseNum(recentBrowseNum);
            paperEntity.setCurrentSerial(freeSerialNum);
            paperEntity.setRenew(true);
            paperDao.save(paperEntity);
        }
    }
}
