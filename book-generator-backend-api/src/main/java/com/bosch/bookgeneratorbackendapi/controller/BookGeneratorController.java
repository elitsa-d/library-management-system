package com.bosch.bookgeneratorbackendapi.controller;

import com.bosch.bookgeneratorbackendapi.model.Book;
import com.bosch.bookgeneratorbackendapi.service.BookGeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing books.
 */
@RestController
public class BookGeneratorController {

    private final BookGeneratorService bookGeneratorService;

    public BookGeneratorController(final BookGeneratorService bookGeneratorService) {
        this.bookGeneratorService = bookGeneratorService;
    }

    /**
     * Endpoint with GET mapping. Retrieves a generated book.
     *
     * @return a Book object
     */
    @GetMapping("/books")
    public Book getGeneratedBook() {
        return this.bookGeneratorService.generateBook();
    }
}
