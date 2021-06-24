package com.sabo.catbooru.repository;

import com.sabo.catbooru.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser_Id(Long userId);
    List<Post> findAllByContest_Id(Long contestId, Sort by);

    @Modifying
    @Query("update Post post set post.upvotes = post.upvotes + 1 where post.id = ?1")
    int likePost(Long id);

    @Modifying
    @Query("update Post post set post.upvotes = post.upvotes - 1 where post.id = ?1")
    int unlikePost(Long id);

    List<Post> findByIdIn(List<Long> ids, Sort by);
}
