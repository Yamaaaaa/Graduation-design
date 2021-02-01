package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.PaperInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperInfoDao extends JpaRepository<PaperInfoEntity, Integer> {
    PaperInfoEntity findById(int paperId);
    List<PaperInfoEntity> findTop50ByOrderByUncheckNumDesc();
    List<PaperInfoEntity> findTop50ByOrderByTaggedNumDesc();
    @Query(value = "select sum(tagged_num) from paper_info", nativeQuery = true)
    Integer getTotalTagNum();
    @Query(value = "select sum(uncheck_num) from paper_info", nativeQuery = true)
    Integer getNewTagNum();
}
