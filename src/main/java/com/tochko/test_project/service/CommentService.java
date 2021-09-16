package com.tochko.test_project.service;

import com.tochko.test_project.model.Book;
import com.tochko.test_project.model.Comment;
import com.tochko.test_project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public Comment findByCommentId(Long id) {
        return repository.findByCommentId(id);
    }

    public Page<Comment> findByBook_BookId(Long bookId, Pageable pageable) {
        return repository.findByBook_BookId(bookId,pageable);
    }

    public  Page<Comment> findByText(String title, Pageable pageable) {
        return repository.findByText(title,pageable);
    }

    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    public void delete(Comment comment) {
        repository.delete(comment);
    }


}
