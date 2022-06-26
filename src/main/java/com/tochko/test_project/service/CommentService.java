package com.tochko.test_project.service;

import com.tochko.test_project.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Comment findById(Long id);

    Page<Comment> findByBook_BookId(Long bookId, Pageable pageable);

    Page<Comment> findByText(String title, Pageable pageable);

    Comment save(Comment comment);

    void delete(Comment comment);
}
