package com.tochko.test_project.service;

import com.tochko.test_project.model.Book;
import com.tochko.test_project.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Book findByBookId(Long id) {
        return repository.findByBookId(id);
    }

}
