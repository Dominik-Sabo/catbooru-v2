package com.sabo.catbooru.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="tag")
public class Tag {

    public Tag(){};

    public Tag(Long postId, String tagValue){
        this.postId = postId;
        this.tagValue = tagValue;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name="tagValue")
    private String tagValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
