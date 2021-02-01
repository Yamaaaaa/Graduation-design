package com.example.paper_service.Dao;

import com.example.paper_service.Entity.PaperEntity;
import com.example.paper_service.Entity.SysInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysInfoDao extends JpaRepository<SysInfoEntity, String> {

    SysInfoEntity findByName(String name);
}
