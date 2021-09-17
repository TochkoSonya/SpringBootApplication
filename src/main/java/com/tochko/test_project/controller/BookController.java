package com.tochko.test_project.controller;

import com.tochko.test_project.model.Author;
import com.tochko.test_project.model.Book;
import com.tochko.test_project.repository.AuthorRepository;
import com.tochko.test_project.service.BookService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CommonsLog(topic = "BookLog")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(
                    @RequestParam(required = false) String title,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "3") int size ) {
        try {
            List<Book> books;
            Pageable paging = PageRequest.of(page, size);

            Page<Book> pageBooks;
            if (title == null)
                pageBooks = bookService.findAll(paging);
            else
                pageBooks = bookService.findByTitle(title, paging);

            books = pageBooks.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("books", books);
            response.put("currentPage", pageBooks.getNumber());
            response.put("totalItems", pageBooks.getTotalElements());
            response.put("totalPages", pageBooks.getTotalPages());

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get all books fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
        try {
            Book bookItem = bookService.findById(id);
            if (bookItem != null) {
                return new ResponseEntity(bookItem, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Get book fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book newBook = bookService
                    .save(new Book(book.getTitle(), book.getDescription()));
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Create book fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books/{bookId}/authors/{authorId}")
    public ResponseEntity<Author> addAuthorToBook(@PathVariable("bookId") Long bookId, @PathVariable("authorId") Long authorId) {
        try {
            Author currentAuthor = authorRepository.findByAuthorId(authorId);
            Book currentBook = bookService.findById(bookId);
            currentAuthor.getBooks().add(currentBook);
            currentBook.getAuthors().add(currentAuthor);
            authorRepository.save(currentAuthor);
            bookService.save(currentBook);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Add author to book fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBook(
            @PathVariable(value = "bookId") Long bookId,
            @RequestBody Book bookDetails) {
        try {
            Book updatedBook = bookService.findById(bookId);
            updatedBook.setTitle(bookDetails.getTitle());
            updatedBook.setDescription(bookDetails.getDescription());
            bookService.save(updatedBook);
            return new ResponseEntity<>(updatedBook, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Update book fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Book> deleteBook(@PathVariable(value = "bookId") Long bookId) {
       try {
           Book deletedBook = bookService.findById(bookId);
           bookService.delete(deletedBook);
           return new ResponseEntity<>(null, HttpStatus.OK);
       }
       catch(Exception e) {
           log.error("Delete book fail");
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }



}