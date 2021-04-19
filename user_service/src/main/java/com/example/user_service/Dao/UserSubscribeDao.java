package com.example.user_service.Dao;

import com.example.user_service.Entity.UserSubscribeEntity;
import com.example.user_service.Entity.UserSubscribeEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSubscribeDao extends JpaRepository<UserSubscribeEntity, UserSubscribeEntityPK> {
    @Query(value = "select sub_id from user_subscribe where user_id=?1", nativeQuery = true)
    List<Integer> findAllByUserId(int user_id);
}
