package com.tochko.test_project.service;

import com.tochko.test_project.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Book findById(Long id);

    Book save(Book book);

    void delete(Book book);

    Page<Book> findByTitle(String title, Pageable pageable);

    Page<Book> findAll(Pageable pageable);
}
