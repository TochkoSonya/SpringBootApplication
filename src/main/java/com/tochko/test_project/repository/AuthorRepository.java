package com.tochko.test_project.repository;

import com.tochko.test_project.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByAuthorId(Long id);

    Page<Author> findByLastName(String lastName, Pageable pageable);
}
