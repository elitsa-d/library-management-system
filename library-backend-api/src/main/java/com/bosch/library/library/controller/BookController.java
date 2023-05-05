package com.bosch.library.library.controller;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.exceptions.InvalidBookIdException;
import com.bosch.library.library.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class BookController {
    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return this.bookService.getAllBooks();
    }

    @PostMapping("/books")
    public Book createBook(@RequestBody final Book book) {
        return this.bookService.createBook(book);
    }

    @PutMapping("/books")
    public ResponseEntity<?> editBook(@RequestBody final Book updatedBook) {
        try {
            final Book book = this.bookService.updateBook(updatedBook);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } catch (final InvalidBookIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable final Long id) {
        try {
            final Long deletedBookId = this.bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedBookId);
        } catch (final InvalidBookIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
