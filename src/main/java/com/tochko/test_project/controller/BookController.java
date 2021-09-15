package com.tochko.test_project.controller;

import com.tochko.test_project.model.Book;
import com.tochko.test_project.repository.BookRepository;
import com.tochko.test_project.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(
                    @RequestParam(required = false) String title,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "3") int size ) {

        try {
            List<Book> books = new ArrayList<Book>();
            Pageable paging = PageRequest.of(page, size);

            Page<Book> pageBooks;
            if (title == null)
                pageBooks = bookRepository.findAll(paging);
            else
                pageBooks = bookRepository.findByTitle(title, paging);

            books = pageBooks.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("books", books);
            response.put("currentPage", pageBooks.getNumber());
            response.put("totalItems", pageBooks.getTotalElements());
            response.put("totalPages", pageBooks.getTotalPages());

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
        Optional<Book> bookItem = bookRepository.findById(id);

        if (bookItem.isPresent()) {
            return new ResponseEntity<>(bookItem.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book newBook = bookRepository
                    .save(new Book(book.getTitle(), book.getDescription()));
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
