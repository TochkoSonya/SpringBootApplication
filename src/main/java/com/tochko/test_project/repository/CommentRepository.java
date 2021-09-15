package com.tochko.test_project.repository;

import com.tochko.test_project.model.Book;
import com.tochko.test_project.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentId(Long id);
    Page<Comment> findByText(String text, Pageable pageable);
    Page<Comment> findAll(Pageable pageable);
}
