package com.example.paper_service.Service;

import com.example.paper_service.Dao.PaperDao;
import com.example.paper_service.Dao.PaperHotDao;
import com.example.paper_service.Dao.PaperSimpleDao;
import com.example.paper_service.Dao.SysInfoDao;
import com.example.paper_service.Entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

@Component
public class PaperService {
    @Autowired
    PaperDao paperDao;
    @Autowired
    PaperHotDao paperHotDao;
    @Autowired
    SysInfoDao sysInfoDao;
    @Autowired
    PaperSimpleDao paperSimpleDao;
    @Autowired
    private RestTemplate restTemplate;

    String userServiceUrl = "http://localhost:50004/";
    String recommendServiceUrl = "http://localhost:50002/";
    String tagServiceUrl = "http://localhost:50003/";
    String getUserHistoryData = "getUserHistory";
    String getPaperRecommendData = "getRecommendPaperIdList";
    String checkTagPaper = "checkTagPaper";
    String getSquarePaperList = "getSquarePaperIdList";
    String addPaperTag = "addPaperTag";
    String getPaperTagData = "getPaperTagData";
    String getSharePaperId = "getUserShare";


    public List<PaperSimpleEntity> getPaperSimpleDataList(List<Integer> paperIDList) {

        return paperSimpleDao.findByIdList(paperIDList);
    }

    public String getPaperData(int paper_id) {
        PaperEntity paperEntity = paperDao.findById(paper_id);
        return paperEntity.getAbst();
    }

    public List<PaperSimpleData> getHotPaperList() {
        List<Integer> paperIdList = new ArrayList<>();
        List<PaperEntity> paperEntityList = paperDao.findTop20ByOrderByRecentBrowseNumDesc();
        for(PaperEntity paperEntity: paperEntityList){
            paperIdList.add(paperEntity.getId());
        }
        //Type type = new TypeToken<Map<Integer, Set<String>>>(){}.getType();
        Gson gson = new Gson();
        String url = recommendServiceUrl + getPaperTagData;
        Map<String, List<String>> paperTagData = new HashMap<>();
        //Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
        paperTagData = restTemplate.postForObject(url, paperIdList, paperTagData.getClass());
        System.out.print("paperTagData: " + paperTagData);
        List<PaperSimpleData> hotPaperList = new ArrayList<>();
        for(PaperEntity paperEntity: paperEntityList){
            PaperSimpleData paperSimpleData = new PaperSimpleData();
            paperSimpleData.setPaperEntity(paperEntity);
            paperSimpleData.setTags(paperTagData.get("" + paperEntity.getId()));
            hotPaperList.add(paperSimpleData);
        }
        return hotPaperList;
    }

//    public List<PaperSimpleEntity> getPaperItemData(List<Integer> paperIdList) {
//        List<PaperItemData> paperListData = new ArrayList<>();
//        for (Integer paperId : paperIdList) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("paper_id", paperId);
//            String url = HttpJob.generateRequestParameters("http", getPaperRecommendData, map);
//            PaperRecommendData paperRecommendData = restTemplate.getForObject(url, PaperRecommendData.class);
//            assert paperRecommendData != null;
//            paperListData.add(new PaperItemData(paperDao.findById((int) paperId));
//        }
//        return paperListData;
//    }
//
//    public List<PaperItemData> getPaperListData(List<PaperEntity> paperEntityList) {
//        List<PaperItemData> paperListData = new ArrayList<>();
//        for (PaperEntity paperEntity : paperEntityList) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("paper_id", paperEntity.getId());
//            String url = HttpJob.generateRequestParameters("http", getPaperRecommendData, map);
//            PaperRecommendData paperRecommendData = restTemplate.getForObject(url, PaperRecommendData.class);
//            assert paperRecommendData != null;
//            paperListData.add(new PaperItemData(paperEntity, paperRecommendData));
//        }
//        return paperListData;
//    }

    public List<PaperSimpleEntity> getPaperPage(int pageNum, int pageSize) {
        return paperSimpleDao.findAll(PageRequest.of(pageNum, pageSize)).getContent();
    }

    public List<PaperEntity> getManagePaperPage(int pageNum, int pageSize) {
        return paperDao.findAll(PageRequest.of(pageNum, pageSize)).getContent();
    }

    public List<PaperEntity> getManagePaperPage(List<Integer> paperIdList) {
        return paperDao.findAllById(paperIdList);
    }

    public void increaseBrowseNum(int paperID) {
        PaperEntity paperEntity = paperDao.findById(paperID);
        paperEntity.setBrowseNum(paperEntity.getRecentBrowseNum() + 1);
        paperDao.save(paperEntity);
        int serNum = sysInfoDao.findByName("current_ser_num").getVal();

        PaperHotEntity paperHotEntity = paperHotDao.findByPaperIdAndSerNum(paperID, serNum);
        if(paperHotEntity == null){
            PaperHotEntity temp = new PaperHotEntity();
            temp.setBrowseNum(0);
            temp.setPaperId(paperID);
            temp.setSerNum(serNum);
            paperHotEntity = paperHotDao.save(temp);
        }
        paperHotEntity.setBrowseNum(paperHotEntity.getBrowseNum() + 1);
        paperHotDao.save(paperHotEntity);
    }

    public void updatePaperRecentBrowseNum() {
        List<Integer> IdList = paperDao.findAllId();
        SysInfoEntity sysInfoEntity = sysInfoDao.findByName("current_ser_num");
        int currentSerNum = sysInfoEntity.getVal();
        int paperHotTW = sysInfoDao.findByName("paper_hot_tw").getVal();
        int newSerNum = (currentSerNum + 1) % paperHotTW;

        for (Integer id : IdList) {
            PaperEntity paperEntity = paperDao.findById((int) id);
            paperEntity.setRecentBrowseNum(paperHotDao.getSumBrowseNumByPaperId(id));

            PaperHotEntity paperHotEntity = paperHotDao.findByPaperIdAndSerNum(id, newSerNum);
            paperHotEntity.setBrowseNum(0);
            paperHotDao.save(paperHotEntity);
            sysInfoEntity.setVal(newSerNum);
            sysInfoDao.save(sysInfoEntity);
            paperDao.save(paperEntity);
        }
    }

    public void addPaper(List<PaperImportData> paperImportData) throws Exception {
        Map<Integer, Set<String>> paperTagData = new HashMap<>();
        for(PaperImportData paperImportData1: paperImportData){
            PaperEntity paperEntity = paperDao.save(new PaperEntity(paperImportData1.getTitle(), paperImportData1.getAbst(), 0, 0));
            paperTagData.put(paperEntity.getId(), paperImportData1.getTags());
            paperImportData1.setId(paperEntity.getId());
        }
        restTemplate.postForObject(checkTagPaper, paperTagData, void.class);
    }


    private static final String[] txtNames = {"航空航天+超音速客机+音爆.txt", "航空航天+复合材料+化学.txt", "航空航天+空间对接+动力学+仿真技术.txt",
            "航空航天+微波光子+卫星通信.txt", "航空航天+医学+人体工程.txt", "化学+复合材料+表面处理.txt", "化学+核事故+应急机制.txt", "化学+化学污染+生态环境.txt",
            "化学+能源科学+专业教学.txt", "化学+生物学+细胞自噬.txt", "医学+仿真技术+实验教学.txt", "医学+禽流感+化学+遗传进化.txt", "医学+人体冷冻+伦理学+法律.txt",
            "医学+数值模拟+人工心脏+磁悬浮.txt", "医学+医患关系+公共治理.txt"};

    public void initData() {
        Map<Integer, Set<String>> paperTagData = new HashMap<>();
        int id = 1;
        for (String txtName : txtNames) {
            File file = new File("paperinfos\\" + txtName);
            String[] tags = txtName.split("\\+|\\.");
            Set<String> paperTag = new HashSet<>();
            // Print the tags' names;
            System.out.print("The tags group: ");
            for (int i = 0; i < tags.length - 1; i++) {
                System.out.print(tags[i] + " ");
                paperTag.add(tags[i]);
            }
            System.out.println("");

            try {
                FileReader reader = new FileReader(file);
                int fileLen = (int) file.length();
                char[] chars = new char[fileLen];
                try {
                    reader.read(chars);
                    // Get all the file's content
                    String allInfos = (String.valueOf(chars));
                    // Split each of the papers
                    String[] papers = allInfos.split("\\$");

                    for (String paper : papers) {
                        // Split the title, download link and abstract of each paper
                        String[] infos = paper.split("@");
                        //System.out.println("划分结果个数："+infos.length);
                        if (infos[2].length() > 1500) {
                            infos[2] = infos[2].substring(0, 1500);
                        }
                        // Create and insert each paper
                        PaperEntity aPaper = new PaperEntity(infos[0], infos[2], 0, 0);
                        System.out.println("Now insert the paper and relation: " + infos[0]);
                        System.out.println("relation data " + paperTag);
                        paperDao.save(aPaper);
                        paperTagData.put(id++, paperTag);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        System.out.println("paperTagData" + paperTagData);
        restTemplate.postForObject(tagServiceUrl + addPaperTag, paperTagData, void.class);
    }

//    public void createPaperIndexDB(PaperImportData paperImportdata) throws Exception {
//        //把数据填充到JavaBean对象中
//        Document document = LuceneUtils.javaBean2Document(paperImportdata);
//        /**
//         * IndexWriter将我们的document对象写到硬盘中
//         *
//         * 参数一：Directory d,写到硬盘中的目录路径是什么
//         * 参数二：Analyzer a, 以何种算法来对document中的原始记录表数据进行拆分成词汇表
//         * 参数三：MaxFieldLength mfl 最多将文本拆分出多少个词汇
//         *
//         * */
//        IndexWriter indexWriter = new IndexWriter(LuceneUtils.getDirectory(), LuceneUtils.getIwc());
//        //将Document对象通过IndexWriter对象写入索引库中
//        indexWriter.addDocument(document);
//        //关闭IndexWriter对象
//        indexWriter.close();
//    }

//    //更新某篇论文的索引（标签索引与主题索引）
//    public void updatePaperIndex(List<Map<String, String>> papersData) throws IOException {
//        String IdKeyWord = "id";
//        DirectoryReader reader = DirectoryReader.open(LuceneUtils.getDirectory());
//        //创建IndexSearcher对象
//        IndexSearcher indexSearcher = new IndexSearcher(reader);
//        for(Map<String, String> paperData: papersData) {
//            String paperId = paperData.get(IdKeyWord);
//            Query query = new TermQuery(new Term("id", paperId));
//            TopDocs topDocs = indexSearcher.search(query, 1);
//            assert topDocs.totalHits == 1;
//            Document doc = indexSearcher.doc(topDocs.scoreDocs[0].doc);
//            IndexWriter indexWriter = new IndexWriter(LuceneUtils.getDirectory(), LuceneUtils.getIwc());
//            for (Map.Entry<String, String> entry : paperData.entrySet()) {
//                indexWriter.updateDocument(new Term(entry.getKey(), entry.getValue()), doc);
//            }
//        }
//    }
//
//    public List<Integer> searchPaper(String searchText) throws Exception {
//        DirectoryReader reader = DirectoryReader.open(LuceneUtils.getDirectory());
//        //创建IndexSearcher对象
//        IndexSearcher indexSearcher = new IndexSearcher(reader);
//        //创建QueryParser对象
//        String[] fields = {"title", "abst", "tags", "topics"};
//        Query query = new MultiFieldQueryParser(fields, LuceneUtils.getAnalyzer()).parse(searchText);
//        TopDocs topDocs = indexSearcher.search(query, 20);
//        //获取符合条件的编号
//        List<Integer> paperIdList = new ArrayList<>();
//        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
//            ScoreDoc scoreDoc = topDocs.scoreDocs[i];
//            int no = scoreDoc.doc;
//            //用indexSearcher对象去索引库中查询编号对应的Document对象
//            Document document = indexSearcher.doc(no);
//
//            paperIdList.add(Integer.parseInt(document.get("id")));
//        }
//        return paperIdList;
//    }

    public List<PaperSimpleData> getRecommendList(int userId, int pageNum){
        String url = recommendServiceUrl + getPaperRecommendData + "?userId=" + userId+"&pageNum="+pageNum;
        Map<String, List<String>> paperIdList = new HashMap<>();
        System.out.println("getRecommendPaperUrl:" + url);
        paperIdList = restTemplate.getForObject(url, paperIdList.getClass());
        System.out.println("paperIdList:" + paperIdList);
        return getPaperSimpleData(paperIdList);
    }

    private List<PaperSimpleData> getPaperSimpleData(Map<String, List<String>> paperIdList) {
        List<PaperSimpleData> paperSimpleDataList = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry: paperIdList.entrySet()){
            PaperSimpleData paperSimpleData = new PaperSimpleData();
            System.out.println("paperId:" + entry.getKey());
            PaperEntity paperEntity = paperDao.findById(Integer.parseInt(entry.getKey()));
            paperSimpleData.setPaperEntity(paperEntity);
            paperSimpleData.setTags(entry.getValue());
            paperSimpleDataList.add(paperSimpleData);
        }
        return paperSimpleDataList;
    }

    public List<SquarePaperData> getSquarePaperList(int userId, int pageNum){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        String url = recommendServiceUrl + getSquarePaperList +"?userId=" + userId +"&pageNum="+pageNum;
        System.out.println("getSquarePaperUrl:" + url);
        Map<String, SquarePaperRecommendData> paperIdList = new HashMap<>();

        String respond = restTemplate.getForObject(url, String.class);
        System.out.println("respond: " + respond);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, SquarePaperRecommendData>>(){}.getType();
        paperIdList = gson.fromJson(respond, type);
        System.out.println("squarePaperTagData: " + paperIdList);
        List<SquarePaperData> squarePaperList = new ArrayList<>();
        for(Map.Entry<String, SquarePaperRecommendData> entry: paperIdList.entrySet()){
            PaperEntity paperEntity = paperDao.findById(Integer.parseInt(entry.getKey()));
            SquarePaperData squarePaperData = new SquarePaperData();
            squarePaperData.setPaperEntity(paperEntity);
            squarePaperData.setSquarePaperRecommendData(entry.getValue());
            squarePaperList.add(squarePaperData);
        }
        return squarePaperList;
    }

    public List<Integer> searchPaper(String searchText){
        searchText = "%" + searchText + "%";
        System.out.println("searchText:" + searchText);
        List<Integer> paperId = new ArrayList<>();
        for(PaperEntity paperEntity: paperDao.findAllByTitleLikeOrAbstLike(searchText, searchText)){
            paperId.add(paperEntity.getId());
        }
        return paperId;
    }

    public List<PaperSimpleData> getUserHistoryData(int userId){
        List<PaperSimpleData> paperSimpleDataList = new ArrayList<>();
        Map<String, List<String>> paperTagData = new HashMap<>();
        paperTagData = restTemplate.getForObject(recommendServiceUrl + getUserHistoryData + "?userId="+userId, paperTagData.getClass());
        return getPaperSimpleData(paperTagData);
    }

    public List<PaperSimpleData> searchPaperForUser(String searchText){
        List<Integer> paperIdList = searchPaper(searchText);
        System.out.println("searchPaperIdList" + paperIdList);
        String url = recommendServiceUrl + getPaperTagData;
        Map<String, List<String>> paperTagData = new HashMap<>();
        //Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
        paperTagData = restTemplate.postForObject(url, paperIdList, paperTagData.getClass());

        List<PaperSimpleData> paperSimpleDataList = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry: paperTagData.entrySet()){
            PaperSimpleData paperSimpleData = new PaperSimpleData();
            paperSimpleData.setPaperEntity(paperDao.findById(Integer.parseInt(entry.getKey())));
            paperSimpleData.setTags(entry.getValue());
            paperSimpleDataList.add(paperSimpleData);
        }

        return paperSimpleDataList;
    }

    public List<PaperSimpleData> userSharePaper(int userId){
        List<Integer> paperIdList = new ArrayList<>();
        paperIdList = restTemplate.getForObject(userServiceUrl + getSharePaperId + "?userId=" + userId, paperIdList.getClass());
        Map<String, List<String>> paperTagData = new HashMap<>();
        //Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
        paperTagData = restTemplate.postForObject(recommendServiceUrl + getPaperTagData, paperIdList, paperTagData.getClass());
        System.out.println("paperTagDataUrl: " + recommendServiceUrl + getPaperTagData);
        System.out.println("paperTagData: " + paperTagData);
        List<PaperSimpleData> paperSimpleDataList = new ArrayList<>();
        for(Integer paperId: paperIdList){
            PaperSimpleData paperSimpleData = new PaperSimpleData();
            paperSimpleData.setPaperEntity(paperDao.findById((int)paperId));
            paperSimpleData.setTags(paperTagData.get("" + paperId));
            paperSimpleDataList.add(paperSimpleData);
        }
        return paperSimpleDataList;
    }
}
