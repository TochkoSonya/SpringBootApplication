package com.tochko.test_project.service;

import com.tochko.test_project.model.Author;
import com.tochko.test_project.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements CommonInterface<Author> {

    @Autowired
    private AuthorRepository repository;

    public Author save(Author author) { return repository.save(author); }
    public void delete(Author author) { repository.delete(author); }
    public Page<Author> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    public Author findById(Long authorId) {
        return repository.findByAuthorId(authorId);
    }
    public Page<Author> findByLastName(String lastName, Pageable pageable) {
        return repository.findByLastName(lastName, pageable);
    }
}
