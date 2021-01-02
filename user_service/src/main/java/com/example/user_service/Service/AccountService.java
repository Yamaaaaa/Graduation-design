package com.example.user_service.Service;

import com.example.user_service.Dao.UserInfoDao;
import com.example.user_service.Entity.UserInfoEntity;
import com.example.user_service.Util.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AccountService implements UserDetailsService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("登录，用户名：" + s);
        if (!userInfoDao.existsByName(s)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        UserInfoEntity systemUser = userInfoDao.findByName(s);
        //String password = new BCryptPasswordEncoder().encode(systemUser.getPassword());
        //System.out.println("加密后密码："+password);
        User user = new User(systemUser.getName(), systemUser.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(systemUser.getRole()));
        return user;
    }

    public boolean signIn(Map params){
        String name = (String) params.get("name");
        String password = (String) params.get("password");
        password = MyPasswordEncoder.getEncoder().encode(password);
        if(userInfoDao.existsByName(name))
            return false;
        userInfoDao.save(new UserInfoEntity(name, password, "user"));
        return true;
    }

    public boolean login(Map params){
        String name = (String) params.get("name");
        String password = (String) params.get("password");
        password = MyPasswordEncoder.getEncoder().encode(password);
        if(!userInfoDao.existsByName(name))
            return false;
        if(userInfoDao.findByName(name).getPassword().equals(password))
            return true;
        return false;
    }
}
