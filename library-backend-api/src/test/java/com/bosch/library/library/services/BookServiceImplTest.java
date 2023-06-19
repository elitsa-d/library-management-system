package com.bosch.library.library.services;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.entities.mappers.BookCreateMapper;
import com.bosch.library.library.entities.mappers.BookMapper;
import com.bosch.library.library.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    BookRepository bookRepository;

    @Spy
    BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @Spy
    BookCreateMapper bookCreateMapper = Mappers.getMapper(BookCreateMapper.class);

    @InjectMocks
    BookServiceImpl bookService;

    List<Book> bookList = new ArrayList<>();

    private static final String DEFAULT_TITLE = "Emma";
    private static final String DEFAULT_AUTHOR = "Jane Austen";
    private static final String DEFAULT_CATEGORY = "Novel";
    private static final String UPDATED_TITLE = "I'd Die for You and Other Lost Stories";
    private static final String UPDATED_AUTHOR = "F. Scott Fitzgerald";
    private static final String UPDATED_CATEGORY = "Short stories";

    @BeforeEach
    void setUp() {
        this.bookList.add(new Book(1L, DEFAULT_TITLE, DEFAULT_AUTHOR, DEFAULT_CATEGORY));
        this.bookList.add(new Book(2L, "Wuthering Heights", "Emily Brontë", "Novel"));
        this.bookList.add(new Book(3L, "Gone with the Wind", "Margaret Mitchell", "Novel"));
    }

    @Test
    void testGetAllBooksReturnsListOfAllBooksWithLengthThree() {
        // Arrange mock repository
        final Page<Book> page = new PageImpl<>(this.bookList);
        Mockito.when(this.bookRepository.findAll(ArgumentMatchers.<Specification<Book>>any(), any(Pageable.class))).thenReturn(page);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        // Retrieve all books
        final int result = this.bookService.getAllBooks(null, pageable).size();

        // Assert that list of returned books has length of 3
        assertEquals(3, result);
    }

    @Test
    void testGetAllBooksReturnsValidListOfAllBooks() {
        // Arrange mock repository
        final Page<Book> page = new PageImpl<>(this.bookList);
        Mockito.when(this.bookRepository.findAll(ArgumentMatchers.<Specification<Book>>any(), any(Pageable.class))).thenReturn(page);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        // Retrieve all books
        final List<BookDTO> result = this.bookService.getAllBooks(null, pageable);
        final List<BookDTO> expectedResult = this.bookMapper.toDTOList(this.bookList);

        // Assert that the right list of books is returned
        assertEquals(expectedResult, result);
    }

    @Test
    void testCreateBookSavesNewBookToRepository() {
        // Arrange
        final BookCreateDTO bookCreateDTO = new BookCreateDTO(UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        final Book book = this.bookCreateMapper.toEntity(bookCreateDTO);

        // Create book
        this.bookService.createBook(bookCreateDTO);

        // Verify that it is saved
        verify(this.bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBookUpdatesAllBookInfo() throws ElementNotFoundException {
        // Arrange mock repository
        final Book book = this.bookList.get(0);
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(this.bookRepository.save(any(Book.class))).thenReturn(book);

        // Change book's data
        final BookDTO updatedBook = new BookDTO(1L, UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        final BookDTO result = this.bookService.updateBook(updatedBook);

        // Assert that all new data is saved and valid
        verify(this.bookRepository, times(1)).save(book);
        assertEquals(UPDATED_TITLE, result.getTitle());
        assertEquals(UPDATED_AUTHOR, result.getAuthor());
        assertEquals(UPDATED_CATEGORY, result.getCategory());
    }

    @Test
    void testUpdateBookUpdatesOnlyTitle() throws ElementNotFoundException {
        // Arrange mock repository
        final Book book = this.bookList.get(0);
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(this.bookRepository.save(any(Book.class))).thenReturn(book);

        // Change book's title
        final BookDTO updatedBook = new BookDTO(1L, UPDATED_TITLE, null, null);
        final BookDTO result = this.bookService.updateBook(updatedBook);

        // Assert that only the title is updated
        assertEquals(UPDATED_TITLE, result.getTitle());
        assertEquals(DEFAULT_AUTHOR, result.getAuthor());
        assertEquals(DEFAULT_CATEGORY, result.getCategory());
    }

    @Test
    void testUpdateBookUpdatesOnlyAuthor() throws ElementNotFoundException {
        // Arrange mock repository
        final Book book = this.bookList.get(0);
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(this.bookRepository.save(any(Book.class))).thenReturn(book);

        // Change book's author
        final BookDTO updatedBook = new BookDTO(1L, null, UPDATED_AUTHOR, null);
        final BookDTO result = this.bookService.updateBook(updatedBook);

        // Assert that only the author is updated
        assertEquals(DEFAULT_TITLE, result.getTitle());
        assertEquals(UPDATED_AUTHOR, result.getAuthor());
        assertEquals(DEFAULT_CATEGORY, result.getCategory());
    }

    @Test
    void testUpdateBookUpdatesOnlyCategory() throws ElementNotFoundException {
        // Arrange mock repository
        final Book book = this.bookList.get(0);
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(this.bookRepository.save(any(Book.class))).thenReturn(book);

        // Change book's category
        final BookDTO updatedBook = new BookDTO(1L, null, null, UPDATED_CATEGORY);
        final BookDTO result = this.bookService.updateBook(updatedBook);

        // Assert that only the category is updated
        assertEquals(DEFAULT_TITLE, result.getTitle());
        assertEquals(DEFAULT_AUTHOR, result.getAuthor());
        assertEquals(UPDATED_CATEGORY, result.getCategory());
    }

    @Test
    void testUpdateBookThrowsOnInvalidBookId() {
        // Arrange mock repository
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that updating nonexistent book throws exception
        final BookDTO updatedBook = new BookDTO(1L, UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        assertThrows(
                ElementNotFoundException.class,
                () -> this.bookService.updateBook(updatedBook),
                "Updating book's data should throw ElementNotFoundException when book optional is empty."
        );
    }

    @Test
    void testDeleteBook() throws ElementNotFoundException {
        // Arrange mock repository
        final Book book = this.bookList.get(0);
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Delete a book
        final Long deletedBookId = this.bookService.deleteBook(1L);

        // Verify that it is deleted
        verify(this.bookRepository, times(1)).delete(book);
        assertEquals(1, deletedBookId);
    }

    @Test
    void testDeleteBookThrowsOnInvalidBookId() {
        // Arrange mock repository
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that deleting nonexistent book throws exception
        assertThrows(
                ElementNotFoundException.class,
                () -> this.bookService.deleteBook(1L),
                "Deleting book should throw ElementNotFoundException when book optional is empty."
        );
    }
}
