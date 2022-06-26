package com.tochko.test_project.service;

import com.tochko.test_project.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    Author save(Author author);

    void delete(Author author);

    Page<Author> findAll(Pageable pageable);

    Author findById(Long authorId);

    Page<Author> findByLastName(String lastName, Pageable pageable);
}
