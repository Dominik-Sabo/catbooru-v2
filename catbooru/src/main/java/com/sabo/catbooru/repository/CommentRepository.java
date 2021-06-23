package com.sabo.catbooru.repository;

import com.sabo.catbooru.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
    List<Comment> findAllByUserId(Long userId);
    void deleteAllByPostId(Long postId);

    @Modifying
    @Query("update Comment comment set comment.username = '<user deleted>', comment.userId = null where comment.userId = ?1")
    void userDelete(long userId);
}
