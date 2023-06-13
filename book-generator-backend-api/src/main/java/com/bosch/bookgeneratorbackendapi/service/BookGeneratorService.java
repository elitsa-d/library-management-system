package com.bosch.bookgeneratorbackendapi.service;

import com.bosch.bookgeneratorbackendapi.model.Book;

/**
 * Service interface for generating books.
 */
public interface BookGeneratorService {

    /**
     * Generates a book with fake data.
     *
     * @return a book object
     */
    Book generateBook();
}
