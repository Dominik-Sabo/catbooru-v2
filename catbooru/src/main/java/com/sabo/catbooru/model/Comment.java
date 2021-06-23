package com.sabo.catbooru.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name="comment")
public class Comment {

    public Comment(){}

    public Comment(Long postId, Long userId, String commentText){
        this.postId = postId;
        this.userId = userId;
        this.commentText = commentText;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="postId")
    private Long postId;

    @Column(name="userId")
    private Long userId;

    @Column(name="username")
    private String username;

    @Column(name="comment_text")
    private String commentText;

    @Column(name="timestamp")
    private Timestamp timestamp = Timestamp.from(Instant.now());

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommentText() {
        return commentText;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
