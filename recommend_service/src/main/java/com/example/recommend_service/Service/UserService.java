package com.example.recommend_service.Service;

import com.example.recommend_service.Dao.*;
import com.example.recommend_service.Entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
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
    PaperTagRelationDao paperTagRelationDao;
    @Autowired
    TopicDao topicDao;
    @Autowired
    SysInfoDao sysInfoDao;
    @Autowired
    UserFeatureDao userFeatureDao;
    @Autowired
    UserDislikePaperDao userDislikePaperDao;
    @Autowired
    UserDislikeTagDao userDislikeTagDao;
    @Autowired
    UserPaperSimilarityDao userPaperSimilarityDao;
    @Autowired
    UserSimilarityDao userSimilarityDao;
    @Autowired
    TagService tagService;
    @Autowired
    private RestTemplate restTemplate;

    private String recommendServiceUrl = "http://localhost:50002/";
    private String accountServiceUrl = "http://localhost:50004/";
    private String paperServiceUrl = "http://localhost:50001/";
    private String getSquarePaperList = "squarePaperList";
    private String getSimplePaperData = "paperSimpleData";
    private float relateValue = 1;
    private float paperTagRelateValue = (float)0.3;
    private int pageSize = 10;

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
        Map<Integer, Float> userTopicFeature = new HashMap<>();
        Set<Integer> userNewTopic = new HashSet<>();
        Map<Integer, Float> userTopicForgetFactors = new HashMap<>();
        List<UserHistoryEntity> userHistoryEntities = userHistoryDao.findByUserIdAndUncheck(userId, true);
        for(UserHistoryEntity userHistoryEntity: userHistoryEntities){
            List<PaperFeatureEntity> paperFeatureEntities = paperFeatureDao.findByPaperId(userHistoryEntity.getPaperId());
            for(PaperFeatureEntity paperFeatureEntity: paperFeatureEntities){
                int topicId = paperFeatureEntity.getTopicId();
                double paperTopicDegree = paperFeatureEntity.getDegree();
                if(paperTopicDegree < paperTopicThreshold)
                    continue;
                if(userTopicFeature.containsKey(topicId)){
                    userTopicFeature.put(topicId, (float)(userTopicFeature.get(topicId) + paperTopicDegree));
                }else{
                    userTopicFeature.put(topicId, paperFeatureEntity.getDegree());
                    float userTopicP;
                    if(userFeatureDao.existsByUserIdAndTopicId(userId, topicId)){
                        UserFeatureEntity userFeatureEntity = userFeatureDao.findByUserIdAndTopicId(userId, topicId);
                        double topicHalfLife = halfLife + differentDays(userFeatureEntity.getLastDate(), new Date()) * halfLifeK;
                        userTopicP = (float)Math.pow(Math.E, -Math.log(2)*(differentDays(userFeatureInfoEntity.getLastRenewDate(), new Date()))/topicHalfLife);

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
        float userTopicP = (float)Math.pow(Math.E, -Math.log(2)*(differentDays(userFeatureInfoEntity.getLastRenewDate(), new Date()))/halfLife);
        int browseNum = userFeatureInfoEntity.getBrowseNum() + userHistoryEntities.size();
        for(UserFeatureEntity userFeatureEntity: userFeatureEntities){
            int topicId = userFeatureEntity.getTopicId();
            float degree = 0;
            float forgetFactor;
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

    public void updateHistory(int userId, Set<Integer> userHistories){
        for(Integer paperId: userHistories){
            userHistoryDao.save(new UserHistoryEntity(userId, paperId, new Date(), true));
        }

        UserFeatureInfoEntity userFeatureInfoEntity = userFeatureInfoDao.findById(userId);
        if(userFeatureInfoEntity == null){
            userFeatureInfoEntity = new UserFeatureInfoEntity();
            userFeatureInfoEntity.setId(userId);
            userFeatureInfoEntity.setBrowseNum(userHistories.size());
            userFeatureInfoEntity.setLastRenewDate(new Date());
        }

        userFeatureInfoEntity.setRenew(true);
        userFeatureInfoDao.save(userFeatureInfoEntity);

        restTemplate.postForObject(recommendServiceUrl + "browseNum", userHistories, void.class);
    }

    public List<PaperSimpleEntity> getHistory(int userId){
        List<Integer> paperIdList = new ArrayList<>();
        for(UserHistoryEntity userHistoryEntity: userHistoryDao.findAllByUserId(userId)){
            paperIdList.add(userHistoryEntity.getPaperId());
        }
        List<PaperSimpleEntity> userHistory = new ArrayList<>();
        return restTemplate.postForObject(paperServiceUrl + getSimplePaperData, paperIdList, userHistory.getClass());
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

    public void initUserTopic(int userId, List<Integer> topics){
        for(Integer topic: topics) {
            userFeatureDao.save(new UserFeatureEntity(userId, topic, (float)1.0, new Date()));
        }
    }

    public List<TopicEntity> getAllTopic(){
        return topicDao.findAll();
    }

    private List<Integer> getAllTopicId(){
        List<Integer> topicIdList = new ArrayList<>();
        for(TopicEntity topicEntity: topicDao.findAll()){
            topicIdList.add(topicEntity.getId());
        }
        return topicIdList;
    }

    public Map<Integer, List<String>> getRecommendPaper(int userId, int pageNum){
        Set<Integer> paperIdList = new HashSet<>();
        List<UserPaperSimilarityEntity> userPaperSimilarityEntities = userPaperSimilarityDao.findByUserIdAndRelateValueGreaterThanOrderByRelateValueDesc(userId, relateValue, PageRequest.of(pageNum, 10));
        for(UserPaperSimilarityEntity userPaperSimilarityEntity: userPaperSimilarityEntities){
            paperIdList.add(userPaperSimilarityEntity.getPaperId());
        }

        List<UserSimilarityEntity> userSimilarityEntities = userSimilarityDao.findByUserIdAndRelateValueGreaterThanOrderByRelateValueDesc(userId, relateValue, PageRequest.of(pageNum, 10));
        for(UserSimilarityEntity userSimilarityEntity: userSimilarityEntities){
            List<UserHistoryEntity> userHistoryEntities = userHistoryDao.findTop3ByUserIdOrderByBrowseTimeDesc(userSimilarityEntity.getUserId());
            for(UserHistoryEntity userHistoryEntity: userHistoryEntities){
                paperIdList.add(userHistoryEntity.getPaperId());
            }
        }

        removeDislike(userId, paperIdList);

        Map<Integer, List<String>> paperData = new HashMap<>();
        for(Integer paperId: paperIdList){
            List<String> tags = paperTagRelationDao.findTagNameByPaperIdAndDegreeGreaterThanEqual(paperId, paperTagRelateValue);
            paperData.put(paperId, tags);
        }

        return paperData;
    }

    public void generateSimilarityData(int userId){
        generateSamePaper(userId);
        getSameUser(userId);
    }

    public Map<Integer, SquarePaperRecommendData> getSquarePaper(int userId, int pageNum){
        List<UserSimilarityEntity> userSimilarityEntities = userSimilarityDao.findByUserIdAndRelateValueGreaterThanOrderByRelateValueDesc(userId, relateValue, PageRequest.of(pageNum, pageSize));

        List<Integer> sameUserIdList = new ArrayList<>();
        for(UserSimilarityEntity userSimilarityEntity: userSimilarityEntities){
            sameUserIdList.add(userSimilarityEntity.getOtherUser());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("pageNum", pageNum);
        map.put("sameUserList", sameUserIdList);
        String url = accountServiceUrl + getSquarePaperList;
        Map<String, UserSubscribeData> squarePaperIdList = new HashMap<>();
        System.out.println("getSquarePaperUrl:" + url);
        System.out.println("data:"+map);
        String respond = restTemplate.postForObject(url, map, String.class);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, UserSubscribeData>>(){}.getType();
        squarePaperIdList = gson.fromJson(respond, type);
        removeDislike(userId, squarePaperIdList);
        System.out.println("squarePaperIdList:"+squarePaperIdList);

        Map<Integer, SquarePaperRecommendData> squarePaperDataList = new HashMap<>();
        for(Map.Entry<String, UserSubscribeData> entry: squarePaperIdList.entrySet()){
            List<String> tags = paperTagRelationDao.findTagNameByPaperIdAndDegreeGreaterThanEqual(Integer.parseInt(entry.getKey()), paperTagRelateValue);
            SquarePaperRecommendData squarePaperRecommendData = new SquarePaperRecommendData();
            squarePaperRecommendData.setUserSubscribeData(entry.getValue());
            squarePaperRecommendData.setTags(tags);
            squarePaperDataList.put(Integer.parseInt(entry.getKey()), squarePaperRecommendData);
        }
        return squarePaperDataList;
    }

    private void generateSamePaper(int userId){
        Map<Integer, Float> result = new HashMap<>();
        List<Integer> topicIdList = getAllTopicId();
        List<Float> userRelateValueList = new ArrayList<>();
        for(Integer topicId: topicIdList){
            if(userFeatureDao.existsByUserIdAndTopicId(userId, topicId)){
                userRelateValueList.add(new Float(userFeatureDao.findByUserIdAndTopicId(userId, topicId).getDegree()));
            }else{
                userRelateValueList.add(new Float(0));
            }
        }
        System.out.println("userRelateValueList:" + userRelateValueList);
        List<Integer> paperIdList = paperFeatureDao.findAllPaperId();
        System.out.println("paperNum:" + paperIdList.size());
        List<List<Float>> paperRelateValueList = new ArrayList<>();
        for(Integer paperId: paperIdList){
            List<Float> valueList = new ArrayList<>();
            for(Integer topicId: topicIdList){
                if(paperFeatureDao.existsByPaperIdAndTopicId(paperId, topicId)){
                    valueList.add(new Float(paperFeatureDao.findByPaperIdAndTopicId(paperId, topicId).getDegree()));
                }else{
                    valueList.add(new Float(0));
                }
            }
            paperRelateValueList.add(valueList);
        }

        List<Float> relateList = calculateSimilarity(userRelateValueList, paperRelateValueList);
        System.out.println("relateList:"+relateList);

        for(int i=0; i<paperIdList.size(); ++i){
            UserPaperSimilarityEntity userPaperSimilarityEntity = new UserPaperSimilarityEntity();
            userPaperSimilarityEntity.setUserId(userId);
            userPaperSimilarityEntity.setPaperId(paperIdList.get(i));
            userPaperSimilarityEntity.setRelateValue(relateList.get(i));
            userPaperSimilarityDao.save(userPaperSimilarityEntity);
        }
    }

    private void getSameUser(int userId){
        List<Integer> topicIdList = getAllTopicId();
        List<Float> userRelateValueList = new ArrayList<>();
        List<List<Float>> otherUserRelateValueList = new ArrayList<>();
        for(Integer topicId: topicIdList){
            if(userFeatureDao.existsByUserIdAndTopicId(userId, topicId)){
                userRelateValueList.add(new Float(userFeatureDao.findByUserIdAndTopicId(userId, topicId).getDegree()));
            }else{
                userRelateValueList.add(new Float(0));
            }
        }

        List<Integer> userIdList = userFeatureDao.findAllUserId();
        userIdList.remove(new Integer(userId));
        for(Integer otherUserId: userIdList){
            assert otherUserId!=userId;
            List<Float> otherUserValueList = new ArrayList<>();
            for(Integer topicId: topicIdList) {
                if (userFeatureDao.existsByUserIdAndTopicId(otherUserId, topicId)){
                    otherUserValueList.add(new Float(userFeatureDao.findByUserIdAndTopicId(otherUserId, topicId).getDegree()));
                }else{
                    otherUserValueList.add(new Float(0));
                }
            }
            otherUserRelateValueList.add(otherUserValueList);
        }
        System.out.println("userRelateValueList" + userRelateValueList);
        System.out.println("otherUserRelateValueList" + otherUserRelateValueList);
        List<Float> relateList = calculateSimilarity(userRelateValueList, otherUserRelateValueList);
        System.out.println("userIdList" + userIdList);
        System.out.println("relateList" + relateList);

        for(int i=0; i<userIdList.size(); ++i){
            UserSimilarityEntity userSimilarityEntity = new UserSimilarityEntity();
            userSimilarityEntity.setUserId(userId);
            userSimilarityEntity.setOtherUser(userIdList.get(i));
            userSimilarityEntity.setRelateValue(relateList.get(i));
            userSimilarityDao.save(userSimilarityEntity);
        }
    }

    private List<Float> calculateSimilarity(List<Float> userRelateValueList, List<List<Float>> otherUserRelateValueList){
        List<Float> relateList = new ArrayList<>();
        for(List<Float> valueList: otherUserRelateValueList){
            assert userRelateValueList.size() == valueList.size();
            float relateValue = 0;
            for(int i=0; i<valueList.size(); ++i){
                relateValue += valueList.get(i)*userRelateValueList.get(i);
            }
            relateList.add(relateValue);
        }
        return relateList;
    }

    private void removeDislike(int userId, Set<Integer> paperIdList){
        List<Integer> disPaperList = userDislikePaperDao.findAllPaperIdByUserId(userId);
        List<String> disTagList = userDislikeTagDao.findAllTagNameByUserId(userId);
        Set<Integer> removePaperList = new HashSet<>();
        paperIdList.removeAll(disPaperList);
        boolean flag = false;
        for(Integer paperId: paperIdList){
            for(String tagId: disTagList){
                if(paperTagRelationDao.existsByPaperIdAndTagNameAndDegreeGreaterThan(paperId, tagId, 5)){
                    removePaperList.add(paperId);
                    flag = true;
                    continue;
                }
            }
            if(flag){
                flag = false;
                continue;
            }
        }
        paperIdList.removeAll(removePaperList);
        System.out.println("paperListSize: "+paperIdList.size());
    }

    private void removeDislike(int userId, Map<String, UserSubscribeData> squarePaperList){
        List<Integer> disPaperList = userDislikePaperDao.findAllPaperIdByUserId(userId);
        List<String> disTagList = userDislikeTagDao.findAllTagNameByUserId(userId);
        Set<Integer> removePaperList = new HashSet<>();
        for(Integer paperId: disPaperList){
            squarePaperList.remove(paperId);
        }
        boolean flag = false;
        for(Map.Entry<String, UserSubscribeData> entry: squarePaperList.entrySet()) {
            for (String tagId : disTagList) {
                if (paperTagRelationDao.existsByPaperIdAndTagNameAndDegreeGreaterThan(Integer.parseInt(entry.getKey()), tagId, paperTagRelateValue)) {
                    removePaperList.add(Integer.parseInt(entry.getKey()));
                    flag = true;
                    continue;
                }
            }
            if (flag) {
                flag = false;
                continue;
            }
        }
        for(Integer paperId: removePaperList){
            squarePaperList.remove(paperId);
        }
        System.out.println("paperListSize: "+ squarePaperList.size());
    }

    public void initUserTagData(int userId, List<String> topics){
        for(String topic: topics){
            UserFeatureEntity userFeatureEntity = new UserFeatureEntity();
            userFeatureEntity.setUserId(userId);
            userFeatureEntity.setTopicId(topicDao.findByName(topic).getId());
            userFeatureEntity.setDegree(5f);
            userFeatureEntity.setLastDate(new Date());
            userFeatureDao.save(userFeatureEntity);
        }
    }

    public Map<Integer, Set<String>> getUserHistoryData(int userId){
        Map<Integer, Set<String>> userHistoryData = new HashMap<>();
        for(UserHistoryEntity userHistoryEntity: userHistoryDao.findAllByUserId(userId)){
            Set<String> tagSet = new HashSet<>();
            for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationDao.findByPaperIdAndDegreeGreaterThanEqual(userHistoryEntity.getPaperId(), relateValue)){
                tagSet.add(paperTagRelationEntity.getTagName());
            }
            userHistoryData.put(userHistoryEntity.getPaperId(), tagSet);
        }
        return userHistoryData;
    }

    public void userDislikeAction(UserActionData userActionData){
        int userId = userActionData.getUserId();
        for(Map.Entry<Integer, Boolean> entry: userActionData.getActions().entrySet()){
            UserDislikePaperEntity userDislikePaperEntity = new UserDislikePaperEntity();
            userDislikePaperEntity.setUserId(userId);
            userDislikePaperEntity.setPaperId(entry.getKey());
            if(entry.getValue()) {
                userDislikePaperDao.save(userDislikePaperEntity);
            }else{
                userDislikePaperDao.delete(userDislikePaperEntity);
            }
        }
    }

    public Map<Integer, Set<String>> getUserDislikePaperData(int userId){
        Map<Integer, Set<String>> userDislikePaperData = new HashMap<>();
        for(Integer paperId: userDislikePaperDao.findAllPaperIdByUserId(userId)){
            Set<String> tagSet = new HashSet<>();
            for(PaperTagRelationEntity paperTagRelationEntity: paperTagRelationDao.findByPaperIdAndDegreeGreaterThanEqual(paperId, relateValue)){
                tagSet.add(paperTagRelationEntity.getTagName());
            }
            userDislikePaperData.put(paperId, tagSet);
        }
        return userDislikePaperData;
    }
}
