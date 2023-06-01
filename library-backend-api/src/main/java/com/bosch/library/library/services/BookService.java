package com.bosch.library.library.services;

import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;

import java.util.List;

/**
 * Service interface for managing books.
 */
public interface BookService {

    /**
     * Retrieves a list of books based on the provided criteria.
     *
     * @param bookCriteria the criteria to filter the books
     * @return a list of book DTOs matching the criteria
     */
    List<BookDTO> getAllBooks(BookCriteria bookCriteria);

    /**
     * Creates a new book using the provided information.
     *
     * @param book the information for the book to be created
     * @return the created book DTO
     */
    BookDTO createBook(BookCreateDTO book);

    /**
     * Updates a book using the provided information.
     *
     * @param book the updated book information
     * @return the updated book DTO
     * @throws ElementNotFoundException if the book with the provided id doesn't exist
     */
    BookDTO updateBook(BookDTO book) throws ElementNotFoundException;

    /**
     * Deletes the book with the provided id.
     *
     * @param id the id of the book to be deleted
     * @return the id of the deleted book
     * @throws ElementNotFoundException if the book with the provided id doesn't exist
     */
    Long deleteBook(Long id) throws ElementNotFoundException;
}
