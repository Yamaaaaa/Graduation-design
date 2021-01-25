package com.example.recommend_service.Service;

import com.example.recommend_service.Dao.*;
import com.example.recommend_service.Entity.PaperFeatureEntity;
import com.example.recommend_service.Entity.UserFeatureEntity;
import com.example.recommend_service.Entity.UserFeatureInfoEntity;
import com.example.recommend_service.Entity.UserHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserService {
    @Autowired
    UserHistoryDao userHistoryDao;
    @Autowired
    PaperFeatureDao paperFeatureDao;
    @Autowired
    UserFeatureInfoDao userFeatureInfoDao;
    @Autowired
    SysInfoDao sysInfoDao;
    @Autowired
    UserFeatureDao userFeatureDao;

    public void buildUserFeature(){
        List<UserFeatureInfoEntity> userInfoList = userFeatureInfoDao.findByRenew(true);

        for(UserFeatureInfoEntity userFeatureInfoEntity :userInfoList){
            //兴趣模型在近期更新过
//            if(userFeatureInfoEntity.getLastRenewDate().after(da)){
            buildRecentUserFeature(userFeatureInfoEntity);
//            }else{
//                buildOldUserFeature(userFeatureInfoEntity);
//            }
            userFeatureInfoEntity.setRenew(false);
        }
    }

    private void buildRecentUserFeature(UserFeatureInfoEntity userFeatureInfoEntity){
        int halfLife = sysInfoDao.findByName("half_life").getVal();
        int halfLifeK = sysInfoDao.findByName("half_life_k").getVal();
        double paperTopicThreshold = sysInfoDao.findByName("paper_topic_th").getVal();

        int userId = userFeatureInfoEntity.getId();
        //获取用户本日喜好信息,计算本日兴趣建模，主题对应遗忘因子
        Map<Integer, Double> userTopicFeature = new HashMap<>();
        Set<Integer> userNewTopic = new HashSet<>();
        Map<Integer, Double> userTopicForgetFactors = new HashMap<>();
        List<UserHistoryEntity> userHistoryEntities = userHistoryDao.findByUserIdAndUncheck(userId, true);
        for(UserHistoryEntity userHistoryEntity: userHistoryEntities){
            List<PaperFeatureEntity> paperFeatureEntities = paperFeatureDao.findByPaperId(userHistoryEntity.getPaperId());
            for(PaperFeatureEntity paperFeatureEntity: paperFeatureEntities){
                int topicId = paperFeatureEntity.getTopicId();
                double paperTopicDegree = paperFeatureEntity.getDegree();
                if(paperTopicDegree < paperTopicThreshold)
                    continue;
                if(userTopicFeature.containsKey(topicId)){
                    userTopicFeature.put(topicId, userTopicFeature.get(topicId) + paperTopicDegree);
                }else{
                    userTopicFeature.put(topicId, paperFeatureEntity.getDegree());
                    double userTopicP;
                    if(userFeatureDao.existsByUserIdAndTopicId(userId, topicId)){
                        UserFeatureEntity userFeatureEntity = userFeatureDao.findByUserIdAndTopicId(userId, topicId);
                        double topicHalfLife = halfLife + differentDays(userFeatureEntity.getLastDate(), new Date()) * halfLifeK;
                        userTopicP = Math.pow(Math.E, -Math.log(2)*(differentDays(userFeatureInfoEntity.getLastRenewDate(), new Date()))/topicHalfLife);

                    }else{
                        userTopicP = 0;
                        userNewTopic.add(topicId);
                    }
                    userTopicForgetFactors.put(topicId, userTopicP);
                }
            }
            userHistoryEntity.setUncheck(false);
            userHistoryDao.save(userHistoryEntity);
        }

        //更新用户兴趣建模
        List<UserFeatureEntity> userFeatureEntities = userFeatureDao.findByUserId(userId);
        double userTopicP = Math.pow(Math.E, -Math.log(2)*(differentDays(userFeatureInfoEntity.getLastRenewDate(), new Date()))/halfLife);
        int browseNum = userFeatureInfoEntity.getBrowseNum() + userHistoryEntities.size();
        for(UserFeatureEntity userFeatureEntity: userFeatureEntities){
            int topicId = userFeatureEntity.getTopicId();
            double degree = 0;
            double forgetFactor;
            if(userTopicFeature.containsKey(topicId)){
                degree += userTopicFeature.get(topicId)*userHistoryEntities.size()/browseNum;
                forgetFactor = userTopicForgetFactors.get(topicId);
                userFeatureEntity.setLastDate(new Date());
            }else{
                forgetFactor = userTopicP;
            }
            degree += userFeatureEntity.getDegree()*forgetFactor*userFeatureInfoEntity.getBrowseNum()/browseNum;
            userFeatureEntity.setDegree(degree);
            userFeatureDao.save(userFeatureEntity);
        }
        for(Integer topicId: userNewTopic){
            userFeatureDao.save(new UserFeatureEntity(userId, topicId, userTopicFeature.get(topicId), new Date()));
        }

        userFeatureInfoEntity.setRenew(false);
        userFeatureInfoEntity.setBrowseNum(browseNum);
        userFeatureInfoEntity.setLastRenewDate(new Date());
        userFeatureInfoDao.save(userFeatureInfoEntity);
    }

    private static int differentDays(Date date1,Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
    }

    public void updateHistory(int userId, List<UserHistoryEntity> userHistories){
        for(UserHistoryEntity userHistoryEntity: userHistories){
            userHistoryDao.save(new UserHistoryEntity(userId, userHistoryEntity.getPaperId(), new Date(), true));
        }
        UserFeatureInfoEntity userFeatureInfoEntity = userFeatureInfoDao.findById(userId);
        userFeatureInfoEntity.setRenew(true);
        userFeatureInfoDao.save(userFeatureInfoEntity);
    }

    public List<UserHistoryEntity> getHistory(int userId, int pageNum, int pageSize){
        return userHistoryDao.findAllByUserId(userId, PageRequest.of(pageNum, pageSize)).getContent();
    }

    public void refreshHistory(){
        int userActionDate = sysInfoDao.findByName("user_action_date").getVal();
        Date da = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(da);//把当前时间赋给日历
        calendar.add(calendar.MONTH, -userActionDate);
        da = calendar.getTime();

        userHistoryDao.deleteByBrowseTimeBefore(da);
    }
}
