package com.sabo.catbooru.model;

import javax.persistence.*;

@Entity
@Table(name="upvote")
public class Upvote {

    public Upvote(){}

    public Upvote(Long userId, Long postId){
        this.userId = userId;
        this.postId = postId;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="userId")
    private Long userId;

    @Column(name="postId")
    private Long postId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
