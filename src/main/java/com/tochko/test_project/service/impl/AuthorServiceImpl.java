package com.tochko.test_project.service.impl;

import com.tochko.test_project.model.Author;
import com.tochko.test_project.repository.AuthorRepository;
import com.tochko.test_project.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(Author author) {
        authorRepository.delete(author);
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public Author findById(Long authorId) {
        return authorRepository.findByAuthorId(authorId);
    }

    @Override
    public Page<Author> findByLastName(String lastName, Pageable pageable) {
        return authorRepository.findByLastName(lastName, pageable);
    }
}
