package com.bosch.library.library.services;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.exceptions.ElementNotFoundException;

import java.util.List;

public interface BookService {
    public List<Book> getAllBooks();

    public Book createBook(Book customer);

    public Book updateBook(Book customer) throws ElementNotFoundException;

    public Long deleteBook(Long id) throws ElementNotFoundException;
}
