package com.example.user_service.Dao;

import com.example.user_service.Entity.UserShareEntity;
import com.example.user_service.Entity.UserShareEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserShareDao extends JpaRepository<UserShareEntity, UserShareEntityPK> {
    List<UserShareEntity> findTop3ByUserId(int userId);
    List<UserShareEntity> findAllByUserId(int userId);
}
