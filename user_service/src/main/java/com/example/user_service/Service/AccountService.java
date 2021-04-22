package com.example.user_service.Service;

import com.example.user_service.Dao.UserDisDao;
import com.example.user_service.Dao.UserInfoDao;
import com.example.user_service.Dao.UserShareDao;
import com.example.user_service.Dao.UserSubscribeDao;
import com.example.user_service.Entity.*;
import com.example.user_service.Util.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AccountService implements UserDetailsService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserSubscribeDao userSubscribeDao;
    @Autowired
    private UserShareDao userShareDao;
    @Autowired
    private UserDisDao userDisDao;

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

    public UserInfoEntity getUserInfo(String userName){
        return userInfoDao.findByName(userName);
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

    public Map<Integer, UserSubscribeData> getSquarePaperList(int user_id, int pageNum, List<Integer> sameUserList){
        Map<Integer, UserSubscribeData> paperSet = new HashMap<>();
        Set<Integer> recommendUserSet = new HashSet<>();
        List<Integer> subUserList = userSubscribeDao.findAllByUserId(user_id);
        recommendUserSet.addAll(subUserList);
        recommendUserSet.addAll(sameUserList);
        List<UserInfoEntity> list = userInfoDao.findTop10ByOrderBySubNumDesc();
        for(UserInfoEntity userInfoEntity: list){
            recommendUserSet.add(userInfoEntity.getId());
        }
        recommendUserSet.remove(userDisDao.findAllByUserId(user_id));
        for(Integer recommendUserId: recommendUserSet){
            for(UserShareEntity userShareEntity: userShareDao.findByUserId(recommendUserId, PageRequest.of(pageNum, 4))){
                if(!paperSet.containsKey(userShareEntity.getPaperId())) {
                    UserSubscribeData userSubscribeData = new UserSubscribeData();
                    userSubscribeData.setUserId(userShareEntity.getUserId());
                    userSubscribeData.setUserName(userInfoDao.findById(userShareEntity.getUserId()).getName());
                    if(subUserList.contains(userShareEntity.getUserId())) {
                        userSubscribeData.setSubscribe(true);
                        paperSet.put(userShareEntity.getPaperId(), userSubscribeData);
                    }else{
                        userSubscribeData.setSubscribe(false);
                        paperSet.put(userShareEntity.getPaperId(), userSubscribeData);
                    }
                }
            }
        }
        return paperSet;
    }

    public void dislike(int userId, int disId){
        userDisDao.save(new UserDisEntity(userId, disId));
    }

    public void share(UserActionData userActionData){
        int userId = userActionData.getUserId();
        for(Map.Entry<Integer, Boolean> entry: userActionData.getActions().entrySet()){
            UserShareEntity userShareEntity = new UserShareEntity();
            userShareEntity.setUserId(userId);
            userShareEntity.setPaperId(entry.getKey());
            if(entry.getValue()){
                userShareDao.save(userShareEntity);
            }else{
                userShareDao.delete(userShareEntity);
            }
        }
    }

    public List<Integer> getShare(int userId){
        List<Integer> paperIdList = new ArrayList<>();
        for(UserShareEntity userShareEntity : userShareDao.findAllByUserId(userId)){
            paperIdList.add(userShareEntity.getPaperId());
        }
        return paperIdList;
    }

    public void addSubscribe(UserActionData userActionData){
        int userId = userActionData.getUserId();
        for(Map.Entry<Integer, Boolean> entry: userActionData.getActions().entrySet()){
            UserSubscribeEntity userSubscribeEntity = new UserSubscribeEntity();
            userSubscribeEntity.setUserId(userId);
            userSubscribeEntity.setSubId(entry.getKey());
            if(entry.getValue()){
                userSubscribeDao.save(userSubscribeEntity);
            }else{
                userSubscribeDao.delete(userSubscribeEntity);
            }
        }
    }

    public List<UserSubscribeData> getSubscribe(int userId){
        List<UserSubscribeData> result = new ArrayList<>();
        List<Integer> usersId = userSubscribeDao.findAllByUserId(userId);
        for(Integer id: usersId){
            if(userInfoDao.findById((int)id) == null)
                continue;
            UserSubscribeData userSubscribeData = new UserSubscribeData();
            userSubscribeData.setUserId(id);
            userSubscribeData.setUserName(userInfoDao.findById((int)id).getName());
            userSubscribeData.setSubscribe(true);
            result.add(userSubscribeData);
        }
        return result;
    }
}
