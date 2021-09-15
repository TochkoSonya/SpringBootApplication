package com.tochko.test_project.model;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    public Book(){}

    public void setBookId(Long bookId) {
        this.bookId=bookId;
    }

    public void setTitle(String title) {
        this.title=title;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Book [id=" + bookId + ", title=" +
                title + ", description=" + description + "]";
    }
}
