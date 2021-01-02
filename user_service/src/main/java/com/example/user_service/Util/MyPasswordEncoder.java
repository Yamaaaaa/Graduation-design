package com.example.user_service.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyPasswordEncoder {
    public static BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
}
