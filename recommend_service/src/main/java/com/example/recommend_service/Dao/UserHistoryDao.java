package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.UserHistoryEntity;
import com.example.recommend_service.Entity.UserHistoryEntityPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserHistoryDao extends JpaRepository<UserHistoryEntity, UserHistoryEntityPK> {
    List<UserHistoryEntity> findByUserIdAndUncheck(int userId, boolean uncheck);
    Page<UserHistoryEntity> findAllByUserId(int userId, Pageable pageable);
    void deleteByBrowseTimeBefore(Date date);
}
