package com.example.recommend_service.Dao;

import com.example.recommend_service.Entity.UserDislikePaperEntityPK;
import com.example.recommend_service.Entity.UserDislikeTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDislikeTagDao extends JpaRepository<UserDislikeTagEntity, UserDislikePaperEntityPK> {
    @Query(nativeQuery = true, value="select tag_name from user_dislike_tag where user_id=?1")
    List<String> findAllTagNameByUserId(int userId);
}
