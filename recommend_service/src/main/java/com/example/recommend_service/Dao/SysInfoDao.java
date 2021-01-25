package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.SysInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysInfoDao extends JpaRepository<SysInfoEntity, String> {
    SysInfoEntity findByName(String name);
}
