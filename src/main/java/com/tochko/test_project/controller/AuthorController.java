package com.tochko.test_project.controller;

import com.tochko.test_project.model.Author;
import com.tochko.test_project.model.Book;
import com.tochko.test_project.service.AuthorService;
import com.tochko.test_project.service.BookService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AuthorController(AuthorService authorService,
                            BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Author>> getAllAuthors(
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
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
            log.error("Get all authors fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") long authorId) {
        try {
            Author authorItem = authorService.findById(authorId);
            if (authorItem != null) {
                log.error("Get author success");
                return new ResponseEntity(authorItem, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Get author fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        try {
            Author newAuthor = authorService
                    .save(new Author(author.getFirstName(), author.getLastName()));
            return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Create author fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<Author> addBookToAuthor(@PathVariable("authorId") Long authorId,
                                                  @PathVariable("bookId") Long bookId) {
        try {
            Author currentAuthor = authorService.findById(authorId);
            Book currentBook = bookService.findById(bookId);
            currentAuthor.getBooks().add(currentBook);
            currentBook.getAuthors().add(currentAuthor);
            authorService.save(currentAuthor);
            bookService.save(currentBook);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Add book to author fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable(value = "id") Long authorId,
            @RequestBody Author authorDetails) {
        try {
            Author updatedAuthor = authorService.findById(authorId);
            updatedAuthor.setFirstName(authorDetails.getFirstName());
            updatedAuthor.setLastName(authorDetails.getLastName());
            authorService.save(updatedAuthor);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Update author fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable(value = "id") Long authorId) {
        try {
            Author deletedAuthor = authorService.findById(authorId);
            authorService.delete(deletedAuthor);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Delete author fail");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

