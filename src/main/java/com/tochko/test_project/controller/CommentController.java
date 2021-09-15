package com.tochko.test_project.controller;

import com.tochko.test_project.model.Book;
import com.tochko.test_project.model.Comment;
import com.tochko.test_project.repository.BookRepository;
import com.tochko.test_project.repository.CommentRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/comments/{bookId}/comment")
    public ResponseEntity<Comment> createComment(@PathVariable("bookId") long bookId, @RequestBody Comment comment) {
       try {
           Optional<Book> optionalBook = bookRepository.findById(bookId);
           if (!optionalBook.isPresent()) {
               return ResponseEntity.unprocessableEntity().build();
           }
           comment.setBook(optionalBook.get());

           Comment newComment = commentRepository.save(comment);
           return new ResponseEntity<>(newComment, HttpStatus.CREATED);
       }
       catch (Exception e) {
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @PutMapping("/comments/{bookId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "bookId") Long bookId,
                               @PathVariable(value = "commentId") Long commentId,
                                 @RequestBody Comment commentRequest) throws ResourceNotFoundException {
        try {
            Optional<Book> optionalBook = bookRepository.findById(bookId);
            Optional<Comment> optionalComment = commentRepository.findById(commentId);
            if (!optionalBook.isPresent() || !optionalComment.isPresent()) {
                //throw new ResourceNotFoundException("bookId not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Comment newComment = commentRepository.findByCommentId(commentId);
            newComment.setText(commentRequest.getText());
            commentRepository.save(newComment);


//            return courseRepository.findById(courseId).map(course - > {
//                    course.setTitle(courseRequest.getTitle());
//            return courseRepository.save(course);
//        }).orElseThrow(() -> new ResourceNotFoundException("course id not found"));

            return new ResponseEntity(optionalComment.get(), HttpStatus.OK);
        }

        catch(Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
