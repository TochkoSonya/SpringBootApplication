package com.tochko.test_project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="authors")
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    public Author() {}

    public Author(String firstName, String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
    }
}
