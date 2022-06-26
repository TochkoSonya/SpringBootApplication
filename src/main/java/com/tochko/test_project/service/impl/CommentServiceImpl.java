package com.tochko.test_project.service.impl;

import com.tochko.test_project.model.Comment;
import com.tochko.test_project.repository.CommentRepository;
import com.tochko.test_project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findByCommentId(id);
    }

    @Override
    public Page<Comment> findByBook_BookId(Long bookId, Pageable pageable) {
        return commentRepository.findByBook_BookId(bookId, pageable);
    }

    @Override
    public Page<Comment> findByText(String title, Pageable pageable) {
        return commentRepository.findByText(title, pageable);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
