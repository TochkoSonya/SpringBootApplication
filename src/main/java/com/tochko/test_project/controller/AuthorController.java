package com.tochko.test_project.controller;

import com.tochko.test_project.model.Author;
import com.tochko.test_project.model.Book;
import com.tochko.test_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors(
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size ) {
        try {
            List<Author> authors;
            Pageable paging = PageRequest.of(page, size);

            Page<Author> pageAuthors;
            if (lastName == null)
                pageAuthors = authorService.findAll(paging);
            else
                pageAuthors = authorService.findByLastName(lastName, paging);

            authors = pageAuthors.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("authors", authors);
            response.put("currentPage", pageAuthors.getNumber());
            response.put("totalItems", pageAuthors.getTotalElements());
            response.put("totalPages", pageAuthors.getTotalPages());

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") long authorId) {
        try {
            Author authorItem = authorService.findByAuthorId(authorId);
            if (authorItem != null) {
                return new ResponseEntity(authorItem, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        try {
            Author newAuthor = authorService
                    .save(new Author(author.getFirstName(),author.getLastName()));
            return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @PostMapping("/authors/books")
//    public ResponseEntity<Author> createAuthorWithBook(@RequestBody Author author) {
//        try {
//            Author newAuthor = new Author();
//            newAuthor.setAuthorId(author.getAuthorId());
//            newAuthor.setFirstName(author.getFirstName());
//            newAuthor.setLastName(author.getLastName());
//            authorRepository.save(newAuthor);
//            Book newBook = author.getBookList().get(1);
//            newAuthor.getBookList().add(newBook);
//            newBook.getAuthors().add(newAuthor);
//            authorRepository.save(newAuthor);
//            bookRepository.save(newBook);
//            return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PutMapping("/authors/{authorId}/books/{bookId}")
    public ResponseEntity<Author> addBookToAuthor(@PathVariable("authorId") Long authorId, @PathVariable("bookId") Long bookId) {
        try {
           Author currentAuthor = authorService.findByAuthorId(authorId);
           Book currentBook = bookService.findByBookId(bookId);
           currentAuthor.getBookList().add(currentBook);
           currentBook.getAuthors().add(currentAuthor);
            authorService.save(currentAuthor);
            bookService.save(currentBook);

           return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/authors/{authorId}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable(value = "authorId") Long authorId,
            @RequestBody Author authorDetails) {
        try {
            Author updatedAuthor = authorService.findByAuthorId(authorId);
            updatedAuthor.setFirstName(authorDetails.getFirstName());
            updatedAuthor.setLastName(authorDetails.getLastName());
            authorService.save(updatedAuthor);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/authors/{authorId}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable(value = "authorId") Long authorId) {
        try {
            Author deletedAuthor = authorService.findByAuthorId(authorId);
            authorService.delete(deletedAuthor);
            return new ResponseEntity<>(deletedAuthor, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
