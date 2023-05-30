package com.bosch.library.library.controller;

import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.services.BookService;
import jakarta.validation.Valid;
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
    public List<BookDTO> getAllBooks(@ModelAttribute final BookCriteria bookCriteria) {
        return this.bookService.getAllBooks(bookCriteria);
    }

    @PostMapping("/books")
    public BookDTO createBook(@Valid @RequestBody final BookCreateDTO bookCreateDTO) {
        return this.bookService.createBook(bookCreateDTO);
    }

    @PatchMapping("/books")
    public ResponseEntity<?> editBook(@RequestBody final BookDTO updatedBook) {
        try {
            final BookDTO bookDTO = this.bookService.updateBook(updatedBook);
            return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable final Long id) {
        try {
            final Long deletedBookId = this.bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedBookId);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
