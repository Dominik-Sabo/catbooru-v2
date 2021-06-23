package com.sabo.catbooru.repository;

import com.sabo.catbooru.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByPostId(Long postId);
    List<Tag> findAllByTagValue(String tagValue);
    void deleteAllByPostId(Long postId);
    void deleteByPostIdAndTagValue(Long postId, String tagValue);
}
