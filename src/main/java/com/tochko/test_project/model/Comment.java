package com.tochko.test_project.model;

import javax.persistence.*;

@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(name="text")
    private String text;

    public Comment() {}

    public Long getCommentId() {
        return commentId;
    }

    public String getText() {
        return text;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment:" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                ' ';
    }
}
