package com.example.tag_service.Controller;

import com.example.tag_service.Entity.TagEntity;
import com.example.tag_service.Servcie.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class TagController {
    @Autowired
    TagService tagService;

    @PostMapping("/tagName")
    List<String> tagName(@RequestBody List<Integer> tagIDList){
        return tagService.getTagList(tagIDList);
    }

    @PostMapping("/addPaperTag")
    void addTag(@RequestBody List<String> tagList, @RequestBody Integer paperId){
        tagService.addTag(paperId, tagList);
    }

    @GetMapping("/tag/mergeTag")
    void mergeTag(@RequestParam int goalTag, @RequestParam int operateTag){
        tagService.mergeTag(goalTag, operateTag);
    }

    @PostMapping("/tag/deleteTag")
    void deleteTag(@RequestBody List<Integer> tagList){
        tagService.deleteTag(tagList);
    }

    @GetMapping("/tagPage")
    List<TagEntity> tagPage(@RequestParam int pageNum, @RequestParam int pageSize){
        return tagService.pageTag(pageNum, pageNum);
    }

    @GetMapping("/tagInfo")
    TagEntity getTagData(@RequestParam int tagId){
        return tagService.getTagInfo(tagId);
    }

    @GetMapping("/SameTagData")
    List<String> getSameTagData(@RequestParam int tagId){
        return tagService.getSameTagData(tagId);
    }

    @GetMapping("/getUncheckList")
    List<Integer> getUncheckList(){
        return tagService.getUncheckList();
    }

    @GetMapping("/getUncheckTag")
    List<Integer> getUncheckTag(@RequestParam int paperId){
        return tagService.getUncheckTagId(paperId);
    }

    @PostMapping("/checkTagPaper")
    void checkTagPaper(@RequestBody Map<Integer, Set<String>> paperTagData){
        for(Map.Entry<Integer, Set<String>> entry: paperTagData.entrySet()) {
            tagService.checkTagPaper(entry.getKey(), entry.getValue());
        }
    }

    @GetMapping("/searchTag")
    List<TagEntity> getUncheckTag(@RequestParam String searchText){
        return tagService.searchTag(searchText);
    }
}
