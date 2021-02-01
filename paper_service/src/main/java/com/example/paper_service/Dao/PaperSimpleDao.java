package com.example.paper_service.Dao;

import com.example.paper_service.Entity.PaperSimpleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaperSimpleDao extends JpaRepository<PaperSimpleEntity, Integer> {
    @Query(nativeQuery = true, value="select * from paper where id in ?1")
    List<PaperSimpleEntity> findByIdList(List<Integer> paperIdList);
}
