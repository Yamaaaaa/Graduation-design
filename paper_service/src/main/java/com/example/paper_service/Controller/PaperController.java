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

//    @GetMapping("/paperPage")
//    public List<PaperSimpleEntity> getPaperPage(@RequestParam int page_num, @RequestParam int page_size){
//        return paperService.getPaperPage(page_num, page_size);
//    }
//
//    @GetMapping("/managePaperPage")
//    public List<PaperEntity> getManagePaperPage(@RequestParam int page_num, @RequestParam int page_size){
//        return paperService.getManagePaperPage(page_num, page_size);
//    }
//
//    @PostMapping("/addPaper")
//    public void addPaper(@RequestBody List<PaperImportData> paperImportData) throws Exception {
//        paperService.addPaper(paperImportData);
//    }

    //搜索论文
    @GetMapping("/searchPaper")
    public List<PaperEntity> searchPaperForManager(@RequestParam String searchText) throws Exception {
        return paperService.getManagePaperPage(paperService.searchPaper(searchText));
    }
}
