package com.sequarius.microblog.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sequarius on 2015/6/7.
 */
@Entity
@Table(name="post")
public class Post {
    @Id
    @GenericGenerator(name="generator",strategy="increment")
    @GeneratedValue(generator="generator")
    private long id;

    @Column(length = 140)
    @Length(max = 140,message = "{comment.content.length.illegal}")
    @NotEmpty(message = "{comment.content.empty.illegal}")
    private String content;

    @OneToOne
    private User user;

    @Column(name="post_time")
    private long postTime;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments=new ArrayList<Comment>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", postTime=" + postTime +
                ", comments=" + comments +
                '}';
    }
}
