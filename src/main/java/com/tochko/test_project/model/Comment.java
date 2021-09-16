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

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

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

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return "Comment:" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                ' ';
    }
}
