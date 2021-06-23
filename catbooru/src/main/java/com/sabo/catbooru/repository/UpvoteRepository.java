package com.sabo.catbooru.repository;

import com.sabo.catbooru.model.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpvoteRepository extends JpaRepository<Upvote, Long> {
    void deleteByUserIdAndPostId(Long userId, Long postId);
    void deleteAllByPostId(Long postId);
    void deleteAllByUserId(Long userId);
    List<Upvote> findAllByUserId(Long userId);
    List<Upvote> findAllByPostId(Long postId);
}
