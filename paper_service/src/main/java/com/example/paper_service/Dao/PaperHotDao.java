package com.example.paper_service.Dao;

import com.example.paper_service.Entity.PaperEntity;
import com.example.paper_service.Entity.PaperHotEntity;
import com.example.paper_service.Entity.PaperHotEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaperHotDao extends JpaRepository<PaperHotEntity, PaperHotEntityPK> {
    PaperHotEntity findByPaperIdAndSerNum(Integer paperId, Integer serNum);
    List<PaperHotEntity> findByPaperId(Integer paperId);
    @Query(value = "select sum(browse_num) from paper_hot where paper_id=?1 and serNum=?2", nativeQuery = true)
    Integer getSumBrowseNumByPaperId(int paperId);
}
