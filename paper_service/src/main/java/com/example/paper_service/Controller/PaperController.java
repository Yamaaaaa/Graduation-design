package com.example.paper_service.Controller;

import com.example.paper_service.Entity.*;
import com.example.paper_service.Service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class PaperController {
    @Autowired
    PaperService paperService;

    @GetMapping("/paperData")
    public String getPaper(@RequestParam int paper_id){
        return paperService.getPaperData(paper_id);
    }

    //获取论文简略信息
    @PostMapping("/paperSimpleData")
    public List<PaperSimpleEntity> getPaperSimpleData(@RequestBody List<Integer> paperIdList){
        return paperService.getPaperSimpleDataList(paperIdList);
    }

    //获取热榜论文
    @GetMapping("/hotPaper")
    public List<PaperSimpleData> getHotPaperList(){
        return paperService.getHotPaperList();
    }

    //获取推荐论文列表
    @GetMapping("/recommendPaper")
    public List<PaperSimpleData> getRecommendList(@RequestParam int userId, @RequestParam int pageNum){
        return paperService.getRecommendList(userId, pageNum);
    }

    //获取广场论文列表
    @GetMapping("/squarePaper")
    public List<SquarePaperData> getSquarePaperList(@RequestParam int userId, @RequestParam int pageNum){
        return paperService.getSquarePaperList(userId, pageNum);
    }

    //浏览论文
    @GetMapping("/browseNum")
    public void addBrowseNum(@RequestParam int paperId){
        paperService.increaseBrowseNum(paperId);
    }

    //用户历史记录论文列表
    @GetMapping("/userHistory")
    public List<PaperSimpleData> getUserHistory(@RequestParam int userId){
        return paperService.getUserHistoryData(userId);
    }

    //搜索论文
    @GetMapping("/searchPaperForManager")
    public List<PaperEntity> searchPaperForManager(@RequestParam String searchText) throws Exception {
        return paperService.getManagePaperPage(paperService.searchPaper(searchText));
    }

    @GetMapping("/searchPaper")
    public List<PaperSimpleData> searchPaper(@RequestParam String searchText){
        return paperService.searchPaperForUser(searchText);
    }

    @GetMapping("/userSharePaper")
    public List<PaperSimpleData> userSharePaper(@RequestParam int userId){
        return paperService.userSharePaper(userId);
    }

}
