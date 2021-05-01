package com.example.tag_service.Controller;

import com.example.tag_service.Entity.TagEntity;
import com.example.tag_service.Entity.TagName;
import com.example.tag_service.Entity.TagPaperEntity;
import com.example.tag_service.Servcie.TagService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.google.gson.reflect.TypeToken;

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
    @GetMapping("/sameTagData")
    List<String> getSameTagData(@RequestParam String tagId){
        return tagService.getSameTagData(tagId);
    }

    //为论文添加tag
    @GetMapping("/getUncheckTag")
    List<String> getUncheckTag(@RequestParam int paperId){
        return tagService.getUncheckTag(paperId);
    }

    @GetMapping("/getTagPaperData")
    List<TagPaperEntity> getTagPaperData(){
        return tagService.getTagPaperData();
    }

    //审核论文Tag
    @PostMapping("/checkTagPaper")
    void checkTagPaper(@RequestBody Map<String, Object> data){
        int paperId = (int)data.get("paperId");
        Gson gson = new Gson();
        List<TagName> tags = (List<TagName>)data.get("tags");
        Type type = new TypeToken<List<TagName>>(){}.getType();
        String temp = gson.toJson(tags);
        System.out.println("tags: " + temp);
        tags = gson.fromJson(temp, type);
        tagService.checkTagPaper(paperId, tags);
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

    @GetMapping("/initData")
    void initData(){
        tagService.initData();
    }
}
