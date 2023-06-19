package com.bosch.library.library.services;

import com.bosch.library.library.clients.BookGeneratorClient;
import com.bosch.library.library.controllers.errors.exceptions.ApiUnavailableException;
import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.entities.mappers.BookCreateMapper;
import com.bosch.library.library.entities.mappers.BookMapper;
import com.bosch.library.library.repositories.BookRepository;
import com.bosch.library.library.repositories.specifications.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final BookCreateMapper bookCreateMapper;

    private final BookMapper bookMapper;

    private final BookGeneratorClient bookGeneratorClient;

    public BookServiceImpl(final BookRepository bookRepository, final BookCreateMapper bookCreateMapper, final BookMapper bookMapper, final BookGeneratorClient bookGeneratorClient) {
        this.bookRepository = bookRepository;
        this.bookCreateMapper = bookCreateMapper;
        this.bookMapper = bookMapper;
        this.bookGeneratorClient = bookGeneratorClient;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDTO> getAllBooks(final BookCriteria bookCriteria, final Pageable pageable) {
        final Page<Book> books = this.bookRepository.findAll(BookSpecification.hasCriteria(bookCriteria), pageable);

        final List<BookDTO> bookDTOs = books
                .stream()
                .map(this.bookMapper::toDTO)
                .collect(Collectors.toList());
        return bookDTOs;
    }

    @Transactional
    @Override
    public BookDTO generateBook() throws ApiUnavailableException {
        final BookCreateDTO bookCreateDTO = this.bookCreateMapper.toDTO(this.bookGeneratorClient.getGeneratedBook());
        return this.createBook(bookCreateDTO);
    }

    @Transactional
    @Override
    public BookDTO createBook(final BookCreateDTO bookCreateDTO) {
        final Book book = this.bookCreateMapper.toEntity(bookCreateDTO);
        return this.bookMapper.toDTO(this.bookRepository.save(book));
    }

    @Transactional
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

    @Transactional
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
