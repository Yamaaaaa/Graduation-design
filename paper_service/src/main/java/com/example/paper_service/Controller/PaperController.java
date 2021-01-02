package com.example.paper_service.Controller;

import com.example.paper_service.Entity.PaperEntity;
import com.example.paper_service.Entity.PaperItemData;
import com.example.paper_service.Entity.PaperSimpleData;
import com.example.paper_service.Service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaperController {
    @Autowired
    PaperService paperService;

    @GetMapping("/paperData")
    public String getPaper(@RequestParam int paper_id){
        return paperService.getPaperData(paper_id);
    }

    @PostMapping("/paperSimpleData")
    public List<PaperSimpleData> getPaperSimpleData(@RequestBody List<Integer> paperIdList){
        return paperService.getPaperSimpleDataList(paperIdList);
    }

    @GetMapping("/hotPaper")
    public List<PaperItemData> getHotPaperList(){
        return paperService.getHotPaperList();
    }

    @GetMapping("/browseNum")
    public void addBrowseNum(@RequestParam int paper_id){
        paperService.increaseBrowseNum(paper_id);
    }

    @GetMapping("/paperPage")
    public List<PaperItemData> getPaperPage(@RequestParam int page_num, @RequestParam int page_size){
        return paperService.getPaperPage(page_num, page_size);
    }
}