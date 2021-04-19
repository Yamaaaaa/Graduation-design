package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.UserDislikePaperEntity;
import com.example.recommend_service.Entity.UserDislikePaperEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDislikePaperDao extends JpaRepository<UserDislikePaperEntity, UserDislikePaperEntityPK> {
    @Query(nativeQuery = true, value="select paper_id from user_dislike_paper where user_id=?1")
    List<Integer> findAllPaperIdByUserId(int userId);
}
