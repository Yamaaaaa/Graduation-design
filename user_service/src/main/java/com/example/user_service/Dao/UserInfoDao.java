package com.example.user_service.Dao;

import com.example.user_service.Entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfoEntity, Integer>{
    UserInfoEntity findById(int id);
    UserInfoEntity findByName(String name);
    boolean existsByName(String name);
    List<UserInfoEntity> findTop10ByOrderBySubNumDesc();
}
