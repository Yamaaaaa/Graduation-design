package com.example.user_service.Dao;

import com.example.user_service.Entity.UserShareEntity;
import com.example.user_service.Entity.UserShareEntityPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserShareDao extends JpaRepository<UserShareEntity, UserShareEntityPK> {
    Page<UserShareEntity> findByUserId(int userId, Pageable pageable);
    List<UserShareEntity> findAllByUserId(int userId);
}
