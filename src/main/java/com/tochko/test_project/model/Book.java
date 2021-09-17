package com.tochko.test_project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId"))
    private List<Author> authors = new ArrayList<>();

    public Book(){}

    public Book(String title, String description){
        this.title=title;
        this.description=description;
    }

}
