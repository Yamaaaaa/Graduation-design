package com.example.recommend_service.Controller;

import com.example.recommend_service.Entity.PaperFeatureData;
import com.example.recommend_service.Entity.PaperInfoEntity;
import com.example.recommend_service.Entity.TopicTagData;
import com.example.recommend_service.Entity.UserHistoryEntity;
import com.example.recommend_service.Service.PaperService;
import com.example.recommend_service.Service.TagService;
import com.example.recommend_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RecommendController {
    @Autowired
    PaperService paperService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;

    @PostMapping("/deleteTagRelation")
    void deleteTag(@RequestBody List<Integer> tags){
        tagService.deleteTag(tags);
    }

    @GetMapping("/mergeTag")
    void mergeTag(@RequestParam int goalTag, @RequestParam int operateTag){
        tagService.mergeTag(goalTag, operateTag);
    }

    @PostMapping("/updateHistory")
    void updateHistory(@RequestBody Integer userId, @RequestBody List<UserHistoryEntity> userHistories){
        userService.updateHistory(userId, userHistories);
    }

    @GetMapping("/getHistoryPaperId")
    List<UserHistoryEntity> getHistory(@RequestParam int userId, @RequestParam int pageNum, @RequestParam int pageSize){
        return userService.getHistory(userId, pageNum, pageSize);
    }

    @GetMapping("/getPaperTag")
    Map<Integer, String> getPaperTag(int paperId){
        return paperService.getPaperRecommendTag(paperId);
    }

    @PostMapping("/addPaperTag")
    void addPaperTag(@RequestBody List<Integer> tags){
        int paperId = tags.get(0);
        tags.remove(0);
        paperService.addPaperTag(paperId, tags);
    }

    @PostMapping("/cluster")
    void cluster(@RequestBody Integer topicNum){
        paperService.IdaTopicCluster(topicNum);
        tagService.refreshTopic(topicNum);
    }

    @PostMapping("/editTopicName")
    void editTopicName(@RequestBody Integer topicId, @RequestBody String name){
        tagService.editTopicName(topicId, name);
    }

    @GetMapping("/getTagPaperData")
    List<PaperInfoEntity> getTagPaperData(){
        return paperService.getTagPaperData();
    }

    @GetMapping("/getTopicTagData")
    List<TopicTagData> getTopicTagData(){
        return tagService.getTopicTagData();
    }

    @GetMapping("/getTagRelatePaper")
    List<Integer> getTagRelatePaper(@RequestParam int tagId, @RequestParam int pageNum, @RequestParam int pageSize){
        return tagService.getTagRelatePaper(tagId, pageNum, pageSize);
    }

    @GetMapping("/getPaperFeatureData")
    List<PaperFeatureData> getPaperFeatureData(@RequestParam int paperId){
        return paperService.getPaperFeatureData(paperId);
    }

}
