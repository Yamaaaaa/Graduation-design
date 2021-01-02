package com.example.tag_service.Controller;

import com.example.tag_service.Dao.TagDao;
import com.example.tag_service.Servcie.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/useTag")
    void useTag(@RequestBody List<Integer> tagList){
        tagService.useTag(tagList);
    }

    @PostMapping("/addTag")
    void addTag(@RequestBody List<String> tagList){
        tagService.addTag(tagList);
    }

    
}
