package com.tochko.test_project.service;

import com.tochko.test_project.model.Book;
import com.tochko.test_project.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Book findByBookId(Long id) {
        return repository.findByBookId(id);
    }
    public Book save(Book book) { return repository.save(book); }
    public void delete(Book book) { repository.delete(book); }

    public Page<Book> findByTitle(String title, Pageable pageable) {
        return repository.findByTitle(title,pageable);
    }
    public  Page<Book> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
