package com.bosch.library.library.services;

import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBooks(BookCriteria bookCriteria);

    BookDTO createBook(BookCreateDTO book);

    BookDTO updateBook(BookDTO book) throws ElementNotFoundException;

    Long deleteBook(Long id) throws ElementNotFoundException;
}
