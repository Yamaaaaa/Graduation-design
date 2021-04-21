package com.example.recommend_service.Controller;

import com.example.recommend_service.Entity.*;
import com.example.recommend_service.Service.PaperService;
import com.example.recommend_service.Service.TagService;
import com.example.recommend_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class RecommendController {
    @Autowired
    PaperService paperService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;

    @PostMapping("/deleteTagRelation")
    void deleteTag(@RequestBody List<String> tags){
        tagService.deleteTag(tags);
    }

    @PostMapping("/mergeTag")
    void mergeTag(@RequestBody Map<String, Object> data){
        tagService.mergeTag((String)data.get("goalTag"), (ArrayList<String>)data.get("operateTag"));
    }

    @PostMapping("/updateHistory")
    void updateHistory(@RequestBody Map<Integer, Set<Integer>> userHistories){
        for(Map.Entry<Integer, Set<Integer>> entry: userHistories.entrySet()){
            userService.updateHistory(entry.getKey(), entry.getValue());
        }
    }

    @GetMapping("/getUserHistory")
    Map<Integer, Set<String>> getHistory(@RequestParam int userId){
        return userService.getUserHistoryData(userId);
    }

    @GetMapping("/getUserDislike")
    Map<Integer, Set<String>> getUserDislike(@RequestParam int userId){
        return userService.getUserDislikePaperData(userId);
    }

    @PostMapping("/userDislike")
    void userDislike(@RequestBody UserActionData userActionData){
        userService.userDislikeAction(userActionData);
    }

    @GetMapping("/getPaperTag")
    List<String> getPaperTag(int paperId){
        return paperService.getPaperRecommendTag(paperId);
    }

    @PostMapping("/addPaperTag")
    void addPaperTag(@RequestBody int paperId, @RequestBody List<String> tags){
        paperService.addPaperTag(paperId, tags);
    }

    @GetMapping("/cluster")
    void cluster(@RequestParam Integer topicNum){
        paperService.IdaTopicCluster(topicNum);
        tagService.refreshTopic(topicNum);
    }

    @PostMapping("/editTopicName")
    void editTopicName(@RequestBody Integer topicId, @RequestBody String name){
        tagService.editTopicName(topicId, name);
    }

    @GetMapping("/getTopicPaperData")
    List<TopicPaperData> getTopicPaperData(){return tagService.getTopicPaperData();}

    @GetMapping("/getTagPaperData")
    List<PaperInfoEntity> getTagPaperData(){
        return paperService.getTagPaperData();
    }

    @GetMapping("/getTopicTagData")
    List<TopicTagData> getTopicTagData(){
        return tagService.getTopicTagData();
    }

    @GetMapping("/getTagRelatePaper")
    List<PaperSimpleEntity> getTagRelatePaper(@RequestParam String tagId){
        return tagService.getTagRelatePaper(tagId);
    }

    @GetMapping("/getTopics")
    List<String> getTopics(){
        return tagService.getTopics();
    }

    @GetMapping("/getTagUsedNum")
    TagUsedData getTagUsedData(@RequestParam String tagId){
        return tagService.getTagUsedData(tagId);
    }

    @GetMapping("/getPaperFeatureData")
    List<PaperFeatureData> getPaperFeatureData(@RequestParam int paperId){
        return paperService.getPaperFeatureData(paperId);
    }

    @GetMapping("/generateSimilarityData")
    void generateSimilarityData(@RequestParam int userId){
        userService.generateSimilarityData(userId);
    }

    @GetMapping("/getRecommendPaperIdList")
    Map<Integer, List<String>> getRecommendPaperIdList(@RequestParam int userId, @RequestParam int pageNum){
        return userService.getRecommendPaper(userId, pageNum);
    }

    @GetMapping("/getSquarePaperIdList")
    Map<Integer, SquarePaperRecommendData> getSquarePaperIdList(@RequestParam int userId, @RequestParam int pageNum){
        return userService.getSquarePaper(userId, pageNum);
    }

    @GetMapping("/getPaperTopicRankData")
    PaperTopicRankData getPaperTopicRankData(@RequestParam int paperId){
        return paperService.getPaperTopicRankData(paperId);
    }

    @GetMapping("/getPaperManageData")
    PaperManageData getPaperManageData(){
        return tagService.getPaperManageData();
    }

    @PostMapping("/initUserTagData")
    void initUserTagData(@RequestBody Map<String, Object> map){
        int userId = (int)map.get("userId");
        List<String> tags = (List<String>) map.get("tags");
        userService.initUserTagData(userId, tags);
    }
    @PostMapping("/initTagData")
    void initTag(@RequestBody Map<Integer, List<String>> tagPaperData){
        paperService.initTagPaper(tagPaperData);
    }

    @GetMapping("/updatePaperTag")
    void updatePaperTag(){
        paperService.updatePaperTag();
    }

    @PostMapping("/getPaperTagData")
    Map<Integer, List<String>> getPaperData(@RequestBody List<Integer> paperIdList){
        return paperService.getPaperTagData(paperIdList);
    }
}
