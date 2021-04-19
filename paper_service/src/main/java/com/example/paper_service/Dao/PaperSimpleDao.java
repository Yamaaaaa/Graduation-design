package com.example.paper_service.Dao;

import com.example.paper_service.Entity.PaperEntity;
import com.example.paper_service.Entity.PaperSimpleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaperSimpleDao extends JpaRepository<PaperSimpleEntity, Integer> {
    @Query(nativeQuery = true, value="select * from paper where id in ?1")
    List<PaperSimpleEntity> findByIdList(List<Integer> paperIdList);

    Page<PaperSimpleEntity> findAll(Pageable pageable);

    PaperSimpleEntity findById(int paperId);
    List<PaperSimpleEntity> findTop20ByOrderByRecentBrowseNumDesc();
}
