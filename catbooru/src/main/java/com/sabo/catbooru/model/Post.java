package com.sabo.catbooru.model;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name="post")
public class Post {

    public Post(){}

    public Post(Long userId){
        this.user = new User(userId);
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="filepath")
    private String filePath;

    @Column(name="upvotes")
    private int upvotes = 0;

    @Column(name="timestamp")
    private Timestamp timestamp = Timestamp.from(Instant.now());

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId(){
        return user.getId();
    };

    public String getUsername(){
        return user.getUsername();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = "/assets/img/" + filePath;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }
}
