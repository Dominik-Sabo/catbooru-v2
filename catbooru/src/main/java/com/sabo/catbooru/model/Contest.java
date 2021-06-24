package com.sabo.catbooru.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name="contest")
public class Contest {

    public Contest(){}

    public Contest(Long contestId){
        this.id = contestId;
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "contest")
    private Set<Post> posts;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="timestart")
    private Timestamp timeStart = Timestamp.from(Instant.now());

    @Column(name="timestop")
    private Timestamp timeStop;

    @Column(name="finished")
    private Boolean finished = false;

    @Column(name="winner")
    private Long winnerId = null;

    public Boolean checkIfActive(){
        Timestamp now = Timestamp.from(Instant.now());
        return now.after(timeStart) && now.before(timeStop);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    public Timestamp getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Timestamp timeStop) {
        this.timeStop = timeStop;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
