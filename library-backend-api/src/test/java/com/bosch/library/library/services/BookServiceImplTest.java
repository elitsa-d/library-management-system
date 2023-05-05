package com.bosch.library.library.services;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.exceptions.InvalidBookIdException;
import com.bosch.library.library.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    BookRepository bookRepository;

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
        Mockito.when(this.bookRepository.findAll()).thenReturn(this.bookList);

        // Retrieve all books
        final int result = this.bookService.getAllBooks().size();

        // Assert that list of returned books has length of 3
        assertEquals(3, result);
    }

    @Test
    void testGetAllBooksReturnsValidListOfAllBooks() {
        // Arrange mock repository
        Mockito.when(this.bookRepository.findAll()).thenReturn(this.bookList);

        // Retrieve all books
        final List<Book> result = this.bookService.getAllBooks();

        // Assert that the right list of books is returned
        assertEquals(this.bookList, result);
    }

    @Test
    void testCreateBookSavesNewBookToRepository() {
        // Arrange
        final Book book = new Book(4L, UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);

        // Create book
        this.bookService.createBook(book);

        // Verify that it is saved
        verify(this.bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBookUpdatesAllBookInfo() throws InvalidBookIdException {
        // Arrange mock repository
        final Book book = this.bookList.get(0);
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(this.bookRepository.save(any(Book.class))).thenReturn(book);

        // Change book's data
        final Book updatedBook = new Book(1L, UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        final Book result = this.bookService.updateBook(updatedBook);

        // Assert that all new data is saved and valid
        verify(this.bookRepository, times(1)).save(book);
        assertEquals(UPDATED_TITLE, result.getTitle());
        assertEquals(UPDATED_AUTHOR, result.getAuthor());
        assertEquals(UPDATED_CATEGORY, result.getCategory());
    }

    @Test
    void testUpdateBookUpdatesOnlyTitle() throws InvalidBookIdException {
        // Arrange mock repository
        final Book book = this.bookList.get(0);
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(this.bookRepository.save(any(Book.class))).thenReturn(book);

        // Change book's title
        final Book updatedBook = new Book(1L, UPDATED_TITLE, null, null);
        final Book result = this.bookService.updateBook(updatedBook);

        // Assert that only the title is updated
        assertEquals(UPDATED_TITLE, result.getTitle());
        assertEquals(DEFAULT_AUTHOR, result.getAuthor());
        assertEquals(DEFAULT_CATEGORY, result.getCategory());
    }

    @Test
    void testUpdateBookUpdatesOnlyAuthor() throws InvalidBookIdException {
        // Arrange mock repository
        final Book book = this.bookList.get(0);
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(this.bookRepository.save(any(Book.class))).thenReturn(book);

        // Change book's author
        final Book updatedBook = new Book(1L, null, UPDATED_AUTHOR, null);
        final Book result = this.bookService.updateBook(updatedBook);

        // Assert that only the author is updated
        assertEquals(DEFAULT_TITLE, result.getTitle());
        assertEquals(UPDATED_AUTHOR, result.getAuthor());
        assertEquals(DEFAULT_CATEGORY, result.getCategory());
    }

    @Test
    void testUpdateBookUpdatesOnlyCategory() throws InvalidBookIdException {
        // Arrange mock repository
        final Book book = this.bookList.get(0);
        Mockito.when(this.bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(this.bookRepository.save(any(Book.class))).thenReturn(book);

        // Change book's category
        final Book updatedBook = new Book(1L, null, null, UPDATED_CATEGORY);
        final Book result = this.bookService.updateBook(updatedBook);

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
        final Book updatedBook = new Book(1L, UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        assertThrows(
                InvalidBookIdException.class,
                () -> this.bookService.updateBook(updatedBook),
                "Updating book's data should throw InvalidBookIdException when book optional is empty."
        );
    }

    @Test
    void testDeleteBook() throws InvalidBookIdException {
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
                InvalidBookIdException.class,
                () -> this.bookService.deleteBook(1L),
                "Deleting book should throw InvalidBookIdException when book optional is empty."
        );
    }
}
