package com.bosch.library.library.services;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.entities.mappers.BookCreateMapper;
import com.bosch.library.library.entities.mappers.BookMapper;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final BookCreateMapper bookCreateMapper;

    private final BookMapper bookMapper;

    public BookServiceImpl(final BookRepository bookRepository, final BookCreateMapper bookCreateMapper, final BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookCreateMapper = bookCreateMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return this.bookMapper.toDTOList(this.bookRepository.findAll());
    }

    @Override
    public BookDTO createBook(final BookCreateDTO bookCreateDTO) {
        final Book book = this.bookCreateMapper.toEntity(bookCreateDTO);
        return this.bookMapper.toDTO(this.bookRepository.save(book));
    }

    @Override
    public BookDTO updateBook(final BookDTO updatedBook) throws ElementNotFoundException {
        final Long id = updatedBook.getId();

        final Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Book with id " + id + " doesn't exist."));

        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }

        if (updatedBook.getAuthor() != null) {
            book.setAuthor(updatedBook.getAuthor());
        }

        if (updatedBook.getCategory() != null) {
            book.setCategory(updatedBook.getCategory());
        }

        return this.bookMapper.toDTO(this.bookRepository.save(book));
    }

    @Override
    public Long deleteBook(final Long id) throws ElementNotFoundException {
        final Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Book with id " + id + " doesn't exist."));

        final List<Customer> customers = book.getWishedBy();

        for (final Customer customer : customers) {
            customer.removeBookFromWishlist(book);
        }

        this.bookRepository.delete(book);
        return id;
    }
}
