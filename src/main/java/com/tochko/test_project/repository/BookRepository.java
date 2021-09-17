package com.tochko.test_project.repository;

import com.tochko.test_project.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByBookId(Long id);
    Page<Book> findByTitle(String title, Pageable pageable);
    Page<Book> findAll(Pageable pageable);

}
