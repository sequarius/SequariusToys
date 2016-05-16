package com.sequarius.microblog.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by Sequarius on 2015/6/7.
 */
@Entity(name = "comment")
public class Comment {
    @Id
    @GenericGenerator(name="generator",strategy="increment")
    @GeneratedValue(generator="generator")
    private long id;
    @OneToOne
    private User author;
    @Column(length = 140)
    @Length(max = 140,message = "{comment.content.length.illegal}")
    @NotEmpty(message = "{comment.content.empty.illegal}")
    private String content;
    @Column(name = "post_time")
    private long postTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }
}
