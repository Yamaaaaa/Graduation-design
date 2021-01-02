package com.example.paper_service.Dao;

import com.example.paper_service.Entity.PaperEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Paper;
import java.util.List;

@Repository
public interface PaperDao extends JpaRepository<PaperEntity, Integer>{
    PaperEntity findById(int id);
    List<PaperEntity> findTop20ByOrderByRecentBrowseNumDesc();
    Page<PaperEntity> findAll(Pageable pageable);
    @Query(nativeQuery = true, value="select id from paper")
    List<Integer> findAllId();
}
