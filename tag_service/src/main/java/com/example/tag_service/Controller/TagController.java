package com.example.tag_service.Controller;

import com.example.tag_service.Entity.TagEntity;
import com.example.tag_service.Servcie.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
public class TagController {
    @Autowired
    TagService tagService;

    //合并标签
    @PostMapping("/mergeTag")
    void mergeTag(@RequestBody Map<String, Object> data){
        tagService.mergeTag((String)data.get("goalTag"), (List<String>)data.get("operateTag"));
    }

    @PostMapping("/deleteTag")
    void deleteTag(@RequestBody List<String> tagList){
        tagService.deleteTag(tagList);
    }

    //获取所有标签信息
    @GetMapping("/tagPage")
    List<TagEntity> tagPage(){
        return tagService.pageTag();
    }

    //获取标签详细信息
    @GetMapping("/tagInfo")
    TagEntity getTagData(@RequestParam String tagId){
        return tagService.getTagInfo(tagId);
    }

    //获取同义词
    @GetMapping("/SameTagData")
    List<String> getSameTagData(@RequestParam String tagId){
        return tagService.getSameTagData(tagId);
    }

    //为论文添加tag
    @GetMapping("/getUncheckTag")
    List<String> getUncheckTag(@RequestParam int paperId){
        return tagService.getUncheckTag(paperId);
    }

    //审核论文Tag
    @PostMapping("/checkTagPaper")
    void checkTagPaper(@RequestBody Map<Integer, Set<String>> paperTagData){
        for(Map.Entry<Integer, Set<String>> entry: paperTagData.entrySet()) {
            tagService.checkTagPaper(entry.getKey(), entry.getValue());
        }
    }

    //为论文添加tag
    @PostMapping("/addPaperTag")
    void addPaperTag(@RequestBody Map<Integer, Set<String>> paperTagData){
        tagService.addUncheckTag(paperTagData);
    }

    //搜索Tag
    @GetMapping("/searchTag")
    List<TagEntity> searchTag(@RequestParam String searchText){
        return tagService.searchTag(searchText);
    }

    @GetMapping("/initTag")
    void initTag(){
        tagService.initTag();
    }

    @GetMapping("/getAllTag")
    List<String> getAllTag(){
        return tagService.getAllTag();
    }

}
