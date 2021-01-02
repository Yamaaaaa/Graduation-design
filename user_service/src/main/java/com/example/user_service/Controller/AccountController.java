package com.example.user_service.Controller;

import com.example.user_service.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
}
