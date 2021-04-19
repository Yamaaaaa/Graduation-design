package com.example.user_service.Dao;

import com.example.user_service.Entity.UserDisEntity;
import com.example.user_service.Entity.UserDisEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDisDao extends JpaRepository<UserDisEntity, UserDisEntityPK> {
    @Query(value = "select dis_id from user_dis where user_id=?1", nativeQuery = true)
    List<Integer> findAllByUserId(int userId);
}
