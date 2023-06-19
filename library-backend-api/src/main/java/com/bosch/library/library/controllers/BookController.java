package com.bosch.library.library.controllers;

import com.bosch.library.library.controllers.errors.exceptions.ApiUnavailableException;
import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
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

    Logger logger = LoggerFactory.getLogger(BookController.class);

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
    public List<BookDTO> getAllBooks(@ModelAttribute final BookCriteria bookCriteria, final Pageable pageable) {
        this.logger.info("Get all books by the following criteria: " + bookCriteria.toString());
        return this.bookService.getAllBooks(bookCriteria, pageable);
    }

    /**
     * Endpoint with POST mapping. Generates a fake book that is persisted in database.
     *
     * @return response entity with the book DTO
     * or error message when the API generating books is unavailable
     */
    @Operation(summary = "Generate a book", description = "This endpoint gives you a book object that is generated with fake data")
    @PostMapping("/books/generate")
    public ResponseEntity<BookDTO> generateBook() throws ApiUnavailableException {
        this.logger.info("Generate a book with fake properties");
        final BookDTO bookDTO = this.bookService.generateBook();
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
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
        this.logger.info("Save a new book based on the following information: " + bookCreateDTO.toString());
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
    public ResponseEntity<BookDTO> editBook(@RequestBody final BookDTO updatedBook) throws ElementNotFoundException {
        this.logger.info("Update book based on the following information: " + updatedBook.toString());
        final BookDTO bookDTO = this.bookService.updateBook(updatedBook);
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
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
    public ResponseEntity<Long> deleteBook(@PathVariable final Long id) throws ElementNotFoundException {
        this.logger.info("Delete book with id " + id);
        final Long deletedBookId = this.bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(deletedBookId);
    }
}
