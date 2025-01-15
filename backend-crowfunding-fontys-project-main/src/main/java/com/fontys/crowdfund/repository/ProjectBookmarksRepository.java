package com.fontys.crowdfund.repository;

import com.fontys.crowdfund.persistence.entity.BookmarkEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectBookmarksRepository extends JpaRepository<BookmarkEntity, Long> {

    @Query("SELECT b  " +
            "FROM  BookmarkEntity b " +
            "WHERE b.user.id = :userId")
    List<BookmarkEntity> getBookmarkedProjects(@Param("userId") int userId);


    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END " +
            "FROM BookmarkEntity b WHERE b.user.id = :userId AND b.project.id = :projectId")
    Boolean isProjectBookmarked(@Param("userId") int userId, @Param("projectId") int projectId);

    @Modifying
    @Transactional
    @Query("DELETE " +
            "FROM BookmarkEntity b " +
            "WHERE b.user.id = :userId AND b.project.id = :projectId")
    void removeProjectBookmark(@Param("projectId")int projectId, @Param("userId")int userId);


}