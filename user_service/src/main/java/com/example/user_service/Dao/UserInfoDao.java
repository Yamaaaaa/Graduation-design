package com.example.user_service.Dao;

import com.example.user_service.Entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfoEntity, Integer>{
    UserInfoEntity findById(int id);
    UserInfoEntity findByName(String name);
    boolean existsByName(String name);
}
