package com.example.tag_service.Dao;

import com.example.tag_service.Entity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TagDao extends JpaRepository<TagEntity, Integer> {
    TagEntity findById(int id);
    TagEntity findByName(String name);
    boolean existsByName(String name);
    List<Integer> deleteByLastActiveTimeBeforeAndUsedNumLessThan(Date date, int num);
    List<TagEntity> findByLastActiveTimeBeforeAndUsedNumLessThan(Date date, int num);
    Page<TagEntity> findAll(Pageable pageable);
}
