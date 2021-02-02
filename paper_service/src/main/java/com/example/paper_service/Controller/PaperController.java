package com.example.paper_service.Controller;

import com.example.paper_service.Entity.PaperEntity;
import com.example.paper_service.Entity.PaperImportData;
import com.example.paper_service.Entity.PaperItemData;
import com.example.paper_service.Entity.PaperSimpleEntity;
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

    @PostMapping("/paperSimpleData")
    public List<PaperSimpleEntity> getPaperSimpleData(@RequestBody List<Integer> paperIdList){
        return paperService.getPaperSimpleDataList(paperIdList);
    }

    @GetMapping("/hotPaper")
    public List<PaperSimpleEntity> getHotPaperList(){
        return paperService.getHotPaperList();
    }

    @GetMapping("/browseNum")
    public void addBrowseNum(@RequestParam int paper_id){
        paperService.increaseBrowseNum(paper_id);
    }

    @GetMapping("/paperPage")
    public List<PaperSimpleEntity> getPaperPage(@RequestParam int page_num, @RequestParam int page_size){
        return paperService.getPaperPage(page_num, page_size);
    }

    @GetMapping("/managePaperPage")
    public List<PaperEntity> getManagePaperPage(@RequestParam int page_num, @RequestParam int page_size){
        return paperService.getManagePaperPage(page_num, page_size);
    }

    @PostMapping("/addPaper")
    public void addPaper(@RequestBody List<PaperImportData> paperImportData) throws Exception {
        paperService.addPaper(paperImportData);
    }

    @PostMapping("/updatePaperIndex")
    public void updatePaperIndex(List<Map<String, String>> paperData) throws IOException {
        paperService.updatePaperIndex(paperData);
    }

    @GetMapping("/user/searchPaper")
    public List<PaperSimpleEntity> searchPaperForUser(@RequestParam String searchText) throws Exception {
        return paperService.getPaperSimpleDataList(paperService.searchPaper(searchText));
    }

    @GetMapping("/manager/searchPaper")
    public List<PaperEntity> searchPaperForManager(@RequestParam String searchText) throws Exception {
        return paperService.getManagePaperPage(paperService.searchPaper(searchText));
    }
}
