package com.bosch.library.library.services;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.exceptions.InvalidBookIdException;
import com.bosch.library.library.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book createBook(final Book book) {
        return this.bookRepository.save(book);
    }

    @Override
    public Book updateBook(final Book updatedBook) throws InvalidBookIdException {
        final Long id = updatedBook.getId();

        final Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new InvalidBookIdException("Book with id " + id + " doesn't exist."));

        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }

        if (updatedBook.getAuthor() != null) {
            book.setAuthor(updatedBook.getAuthor());
        }

        if (updatedBook.getCategory() != null) {
            book.setCategory(updatedBook.getCategory());
        }

        return this.bookRepository.save(book);
    }

    @Override
    public Long deleteBook(final Long id) throws InvalidBookIdException {
        final Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new InvalidBookIdException("Book with id " + id + " doesn't exist."));

        this.bookRepository.delete(book);
        return id;
    }
}
