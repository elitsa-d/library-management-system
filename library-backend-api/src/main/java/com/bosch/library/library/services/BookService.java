package com.bosch.library.library.services;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.exceptions.ElementNotFoundException;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book createBook(Book customer);

    Book updateBook(Book customer) throws ElementNotFoundException;

    Long deleteBook(Long id) throws ElementNotFoundException;
}
