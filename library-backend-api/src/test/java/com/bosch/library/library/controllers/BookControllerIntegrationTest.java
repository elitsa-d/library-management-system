package com.bosch.library.library.controllers;

import com.bosch.library.library.entities.Availability;
import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.repositories.AvailabilityRepository;
import com.bosch.library.library.repositories.BookRepository;
import com.bosch.library.library.repositories.specifications.BookSpecification;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private RestTemplate restTemplate;

    private static final String DEFAULT_TITLE = "Gone with the Wind";
    private static final String DEFAULT_AUTHOR = "Margaret Mitchell";
    private static final String DEFAULT_CATEGORY = "Novel";
    private static final String UPDATED_TITLE = "I'd Die for You and Other Lost Stories";
    private static final String UPDATED_AUTHOR = "F. Scott Fitzgerald";
    private static final String UPDATED_CATEGORY = "Short stories";
    private static final Long MISSING_ID = 15L;

    @Before
    public void setUp() {
        this.restTemplate = this.testRestTemplate.getRestTemplate();
        final HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        final Book book1 = new Book(DEFAULT_TITLE, DEFAULT_AUTHOR, DEFAULT_CATEGORY);
        final Book book2 = new Book("Wuthering Heights", "Emily Brontë", "Novel");
        this.bookRepository.saveAll(List.of(book1, book2));

        this.availabilityRepository.save(new Availability(null, book1, 10));
    }

    @After
    public void emptyData() {
        this.bookRepository.deleteAll();
    }

    @Test
    public void testGetAllBooks() {
        // Prepare data
        final String requestUrl = "/api/books";

        // Perform get request
        final ResponseEntity<List<BookDTO>> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final List<BookDTO> books = response.getBody();
        assertNotNull(books);
        assertEquals(2, books.size());

        // Check that the retrieved data is right
        final BookDTO book = books.get(0);
        assertEquals(DEFAULT_TITLE, book.getTitle());
        assertEquals(DEFAULT_AUTHOR, book.getAuthor());
        assertEquals(DEFAULT_CATEGORY, book.getCategory());
    }

    @Test
    public void testGetAllBooksBySpecificCategory() {
        // Prepare data
        final String requestUrl = "/api/books?category=Novel";

        // Perform get request
        final ResponseEntity<List<BookDTO>> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final List<BookDTO> books = response.getBody();
        assertNotNull(books);
        assertEquals(2, books.size());

        // Check that the retrieved data is right
        final BookDTO book = books.get(0);
        assertEquals(DEFAULT_TITLE, book.getTitle());
        assertEquals(DEFAULT_AUTHOR, book.getAuthor());
        assertEquals(DEFAULT_CATEGORY, book.getCategory());
    }

    @Test
    public void testGetAllBooksBySpecificTitleAndAuthor() {
        // Prepare data
        final String requestUrl = "/api/books?title=Gone&author=Margaret";

        // Perform get request
        final ResponseEntity<List<BookDTO>> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final List<BookDTO> books = response.getBody();
        assertNotNull(books);
        assertEquals(1, books.size());

        // Check that the retrieved data is right
        final BookDTO book = books.get(0);
        assertEquals(DEFAULT_TITLE, book.getTitle());
        assertEquals(DEFAULT_AUTHOR, book.getAuthor());
        assertEquals(DEFAULT_CATEGORY, book.getCategory());
    }

    @Test
    public void testGetAllBooksBySpecificTitleAuthorAndCategory() {
        // Prepare data
        final String requestUrl = "/api/books?title=Gone&author=Margaret&category=Novel";

        // Perform get request
        final ResponseEntity<List<BookDTO>> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final List<BookDTO> books = response.getBody();
        assertNotNull(books);
        assertEquals(1, books.size());

        // Check that the retrieved data is right
        final BookDTO book = books.get(0);
        assertEquals(DEFAULT_TITLE, book.getTitle());
        assertEquals(DEFAULT_AUTHOR, book.getAuthor());
        assertEquals(DEFAULT_CATEGORY, book.getCategory());
    }

    @Test
    public void testGetAllBooksBySpecificTitleAuthorCategoryAndQuantity() {
        // Prepare data
        final String requestUrl = "/api/books?title=Gone&author=Margaret&category=Novel&quantity=10";

        // Perform get request
        final ResponseEntity<List<BookDTO>> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final List<BookDTO> books = response.getBody();
        assertNotNull(books);
        assertEquals(1, books.size());

        // Check that the retrieved data is right
        final BookDTO book = books.get(0);
        assertEquals(DEFAULT_TITLE, book.getTitle());
        assertEquals(DEFAULT_AUTHOR, book.getAuthor());
        assertEquals(DEFAULT_CATEGORY, book.getCategory());
    }

    @Test
    public void testGetAllBooksByInvalidQuantityReturnsEmptyList() {
        // Prepare data
        final String requestUrl = "/api/books?quantity=100";

        // Perform get request
        final ResponseEntity<List<BookDTO>> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and the returned list is empty
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final List<BookDTO> books = response.getBody();
        assertNotNull(books);
        assertEquals(0, books.size());
    }

    @Test
    public void testCreateBook() {
        // Prepare data
        final BookCreateDTO bookCreateDTO = new BookCreateDTO(UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        final String requestUrl = "/api/books";

        // Perform post request
        final ResponseEntity<BookDTO> response = this.testRestTemplate.postForEntity(
                requestUrl,
                bookCreateDTO,
                BookDTO.class
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Check that the data is saved
        final List<Book> savedBook = this.bookRepository.findAll(BookSpecification.hasCriteria(new BookCriteria(UPDATED_TITLE, UPDATED_AUTHOR, null, null)));
        assertNotNull(savedBook);
        assertEquals(bookCreateDTO.getTitle(), savedBook.get(0).getTitle());
        assertEquals(bookCreateDTO.getAuthor(), savedBook.get(0).getAuthor());
        assertEquals(bookCreateDTO.getCategory(), savedBook.get(0).getCategory());
    }

    @Test
    public void testEditBook() {
        // Prepare data
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final BookDTO bookDTO = new BookDTO(book.getId(), UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        final String requestUrl = "/api/books";

        // Perform patch request
        final ResponseEntity<BookDTO> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.PATCH,
                new HttpEntity<>(bookDTO),
                BookDTO.class
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Check that the data is saved
        final List<Book> savedBook = this.bookRepository.findAll(BookSpecification.hasCriteria(new BookCriteria(UPDATED_TITLE, UPDATED_AUTHOR, null, null)));
        assertNotNull(savedBook);
        assertEquals(bookDTO.getTitle(), savedBook.get(0).getTitle());
        assertEquals(bookDTO.getAuthor(), savedBook.get(0).getAuthor());
        assertEquals(bookDTO.getCategory(), savedBook.get(0).getCategory());
    }

    @Test
    public void testEditBookThrowsOnInvalidBookId() {
        // Prepare data
        this.bookRepository.deleteById(MISSING_ID);
        final BookDTO bookDTO = new BookDTO(MISSING_ID, UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        final String requestUrl = "/api/books";

        // Perform patch request
        final ResponseEntity<String> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.PATCH,
                new HttpEntity<>(bookDTO),
                String.class
        );

        // Assert that status code is right and exception is thrown
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        final String errorMessage = response.getBody();
        assertEquals("Book with id " + MISSING_ID + " doesn't exist.", errorMessage);
    }

    @Test
    public void testDeleteBook() {
        // Prepare data
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final String requestUrl = String.format("/api/books/%d", book.getId());

        // Perform delete request
        final ResponseEntity<Long> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.DELETE,
                null,
                Long.class
        );

        // Assert that status code is right and the right data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final Long receivedId = response.getBody();
        assertEquals(book.getId(), receivedId);
    }

    @Test
    public void testDeleteBookThrowsOnInvalidBookId() {
        // Prepare data
        this.bookRepository.deleteById(MISSING_ID);
        final String requestUrl = String.format("/api/books/%d", MISSING_ID);

        // Perform delete request
        final ResponseEntity<String> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.DELETE,
                null,
                String.class
        );

        // Assert that status code is right and exception is thrown
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        final String errorMessage = response.getBody();
        assertEquals("Book with id " + MISSING_ID + " doesn't exist.", errorMessage);
    }
}
