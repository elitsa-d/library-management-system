package com.bosch.library.library.controller;

import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing books.
 */
@Tag(description = "Access and modify information about the different books", name = "Books")
@RequestMapping("/api")
@RestController
public class BookController {
    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Endpoint with GET mapping. Retrieves a list of books based on the criteria, provided in the request body.
     *
     * @param bookCriteria the criteria to filter the books, provided in a BookCriteria object
     * @return a list of book DTOs matching the criteria
     * or a list of all books if no criteria is provided
     */
    @Operation(summary = "Get books", description = "Provide criteria to get books by specific characteristics or provide no criteria to get all books")
    @GetMapping("/books")
    public List<BookDTO> getAllBooks(@ModelAttribute final BookCriteria bookCriteria) {
        return this.bookService.getAllBooks(bookCriteria);
    }

    /**
     * Endpoint with POST mapping. Creates a new book using the information from the request body.
     *
     * @param bookCreateDTO the information for the book to be created, provided in a BookCreateDTO object
     * @return the created book DTO
     */
    @Operation(summary = "Add a new book", description = "Add a book by providing information about its title, author and category")
    @PostMapping("/books")
    public BookDTO createBook(@Valid @RequestBody final BookCreateDTO bookCreateDTO) {
        return this.bookService.createBook(bookCreateDTO);
    }

    /**
     * Endpoint with PATCH mapping. Updates a book using the information from the request body.
     *
     * @param updatedBook the updated book information, provided in a BookDTO object
     * @return response entity with the updated book DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Edit an existing book", description = "Provide the id of the book to be edited and only the information that needs to be updated")
    @PatchMapping("/books")
    public ResponseEntity<?> editBook(@RequestBody final BookDTO updatedBook) {
        try {
            final BookDTO bookDTO = this.bookService.updateBook(updatedBook);
            return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint with DELETE mapping. Deletes the book with the provided id.
     *
     * @param id the id of the book to be deleted
     * @return response entity with the id of the deleted book
     * or error message when the provided id is inaccurate
     */
    @Operation(summary = "Delete a book", description = "Provide the id of the book that should be removed")
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
