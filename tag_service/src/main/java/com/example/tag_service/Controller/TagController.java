package com.example.tag_service.Controller;

import com.example.tag_service.Dao.TagDao;
import com.example.tag_service.Entity.TagEntity;
import com.example.tag_service.Servcie.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TagController {
    @Autowired
    TagService tagService;

    @PostMapping("/tagName")
    Map<Integer, String> tagName(@RequestBody List<Integer> tagIDList){
        return tagService.getTagList(tagIDList);
    }

    @PostMapping("/addPaperTag")
    void addTag(@RequestBody List<String> tagList, @RequestBody Integer paperId){
        tagService.addTag(paperId, tagList);
    }

    @PostMapping("/deleteTag")
    void deleteTag(@RequestBody List<Integer> tagList){
        tagService.deleteTag(tagList);
    }

    @GetMapping("/tagPage")
    List<TagEntity> tagPage(@RequestParam int pageNum, @RequestParam int pageSize){
        return tagService.pageTag(pageNum, pageNum);
    }
}
