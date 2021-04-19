package com.example.user_service.Controller;

import com.example.user_service.Entity.UserInfoEntity;
import com.example.user_service.Entity.UserSubscribeData;
import com.example.user_service.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/signin")
    public boolean signIn(@RequestBody Map params){
        return accountService.signIn(params);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/squarePaperList")
    public Map<Integer, UserSubscribeData> getSquarePaperList(@RequestBody Map<String, Object> map){
        int userId = (int) map.get("userId");
        List<Integer> sameUserList = (List<Integer>) map.get("sameUserList");
        return accountService.getSquarePaperList(userId, sameUserList);
    }

    @PostMapping("/userDislike")
    public void dislike(@RequestBody Integer userId, @RequestBody Integer disId){
        accountService.dislike(userId, disId);
    }

    @PostMapping("/userShare")
    public void share(@RequestBody Integer userId, @RequestBody Integer paperId){
        accountService.share(userId, paperId);
    }

    @PostMapping("/addSubscribe")
    public void addSubscribe(@RequestBody Integer userId, @RequestBody Integer subId){
        accountService.addSubscribe(userId, subId);
    }

    @GetMapping("/userSubscribe")
    public List<UserSubscribeData> getSubscribeData(@RequestParam int userId){
        return accountService.getSubscribe(userId);
    }

    @GetMapping("/userInfo")
    public UserInfoEntity getUserInfo(@RequestParam String userName){
        return accountService.getUserInfo(userName);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Map params){
        return accountService.login(params);
    }
}
