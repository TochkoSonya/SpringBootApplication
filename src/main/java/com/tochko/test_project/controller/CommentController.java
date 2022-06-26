package com.tochko.test_project.controller;

import com.tochko.test_project.model.Book;
import com.tochko.test_project.model.Comment;
import com.tochko.test_project.service.BookService;
import com.tochko.test_project.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public CommentController(BookService bookService,
                             CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<List<Comment>> getCommentsByBook(
            @PathVariable("bookId") Long bookId,
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            List<Comment> comments;
            Pageable paging = PageRequest.of(page, size);

            Page<Comment> pageComments;
            if (text == null)
                pageComments = commentService.findByBook_BookId(bookId, paging);
            else
                pageComments = commentService.findByText(text, paging);

            comments = pageComments.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("comments", comments);
            response.put("currentPage", pageComments.getNumber());
            response.put("totalItems", pageComments.getTotalElements());
            response.put("totalPages", pageComments.getTotalPages());
            return new ResponseEntity(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Get comments fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<Comment> createComment(@PathVariable("bookId") Long bookId,
                                                 @RequestBody Comment comment) {
        try {
            Book optionalBook = bookService.findById(bookId);
            if (optionalBook == null) {
                return ResponseEntity.unprocessableEntity().build();
            }
            comment.setBook(optionalBook);
            Comment newComment = commentService.save(comment);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Create comment fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "id") Long commentId,
                                                 @RequestBody Comment commentRequest) throws ResourceNotFoundException {
        try {
            Comment newComment = commentService.findById(commentId);
            newComment.setText(commentRequest.getText());
            commentService.save(newComment);
            return new ResponseEntity<>(newComment, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Update comment fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "id") Long commentId) {
        try {
            Comment deletedComment = commentService.findById(commentId);
            commentService.delete(deletedComment);
            return new ResponseEntity<>(deletedComment, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Delete comment fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
