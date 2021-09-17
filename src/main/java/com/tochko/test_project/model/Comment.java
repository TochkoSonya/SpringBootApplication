package com.tochko.test_project.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="comments")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(name="text")
    private String text;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

}
