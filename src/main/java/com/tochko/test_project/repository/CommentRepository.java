package com.tochko.test_project.repository;

import com.tochko.test_project.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentId(Long id);
    Page<Comment> findByBook_BookId(Long bookId, Pageable pageable);
    Page<Comment> findByText(String title, Pageable pageable);
}
