package com.tochko.test_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tochko.test_project.model.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByBookId(Long id);
}