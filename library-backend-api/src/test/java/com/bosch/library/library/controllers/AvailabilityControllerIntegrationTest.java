package com.bosch.library.library.controllers;

import com.bosch.library.library.entities.Availability;
import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.AvailabilityByBookDTO;
import com.bosch.library.library.entities.dto.AvailabilityByLocationDTO;
import com.bosch.library.library.entities.dto.AvailabilityCreateDTO;
import com.bosch.library.library.entities.dto.AvailabilityDTO;
import com.bosch.library.library.repositories.AvailabilityRepository;
import com.bosch.library.library.repositories.BookRepository;
import com.bosch.library.library.repositories.LocationRepository;
import com.bosch.library.library.repositories.specifications.BookSpecification;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvailabilityControllerIntegrationTest {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String DEFAULT_TITLE = "Gone with the Wind";
    private static final String DEFAULT_AUTHOR = "Margaret Mitchell";
    private static final String DEFAULT_CATEGORY = "Novel";
    private static final Integer DEFAULT_QUANTITY = 11;
    private static final String DEFAULT_ADDRESS = "Vitoshka 23";
    private static final String UPDATED_TITLE = "I'd Die for You and Other Lost Stories";
    private static final String UPDATED_AUTHOR = "F. Scott Fitzgerald";
    private static final String UPDATED_CATEGORY = "Short stories";
    private static final Integer UPDATED_QUANTITY = 22;
    private static final String UPDATED_ADDRESS = "Evlogi Georgiev 34";
    private static final Long MISSING_ID = 15L;

    @Before
    public void setUp() {
        final Location location = new Location(DEFAULT_ADDRESS);
        this.locationRepository.save(location);

        final Book book = new Book(DEFAULT_TITLE, DEFAULT_AUTHOR, DEFAULT_CATEGORY);
        this.bookRepository.save(book);

        final Availability availability = new Availability(location, book, DEFAULT_QUANTITY);
        this.availabilityRepository.save(availability);
    }

    @After
    public void emptyData() {
        this.availabilityRepository.deleteAll();
        this.locationRepository.deleteAll();
        this.bookRepository.deleteAll();
    }

    @Test
    public void testGetAvailableBooksInLocation() {
        // Prepare data
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final String requestUrl = String.format("/api/availability/books-in-location/%d", location.getId());

        // Perform get request
        final ResponseEntity<List<AvailabilityByLocationDTO>> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final List<AvailabilityByLocationDTO> availabilities = response.getBody();
        assertNotNull(availabilities);
        assertEquals(1, availabilities.size());

        // Check that the retrieved data is right
        final AvailabilityByLocationDTO availability = availabilities.get(0);
        assertEquals(DEFAULT_TITLE, availability.getBook().getTitle());
        assertEquals(DEFAULT_AUTHOR, availability.getBook().getAuthor());
        assertEquals(DEFAULT_CATEGORY, availability.getBook().getCategory());
        assertEquals(DEFAULT_QUANTITY, availability.getQuantity());
    }

    @Test
    public void testGetLocationsWithAvailableBook() {
        // Prepare data
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final String requestUrl = String.format("/api/availability/locations-having-book/%d", book.getId());

        // Perform get request
        final ResponseEntity<List<AvailabilityByBookDTO>> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final List<AvailabilityByBookDTO> availabilities = response.getBody();
        assertNotNull(availabilities);
        assertEquals(1, availabilities.size());

        // Check that the retrieved data is right
        final AvailabilityByBookDTO availability = availabilities.get(0);
        assertEquals(DEFAULT_ADDRESS, availability.getLocation().getAddress());
        assertEquals(DEFAULT_QUANTITY, availability.getQuantity());
    }

    @Test
    public void testAddBookToLocation() {
        // Create new availability
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final Book book = new Book(UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        this.bookRepository.save(book);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), book.getId(), DEFAULT_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform post request
        final ResponseEntity<AvailabilityDTO> response = this.testRestTemplate.postForEntity(
                requestUrl,
                availabilityCreateDTO,
                AvailabilityDTO.class
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Check that the data is saved
        final Availability savedAvailability = this.availabilityRepository.findByLocationIdAndBookId(location.getId(), book.getId());
        assertEquals(book.getTitle(), savedAvailability.getBook().getTitle());
        assertEquals(book.getAuthor(), savedAvailability.getBook().getAuthor());
        assertEquals(book.getCategory(), savedAvailability.getBook().getCategory());
        assertEquals(location.getAddress(), savedAvailability.getLocation().getAddress());
        assertEquals(availabilityCreateDTO.getQuantity(), savedAvailability.getQuantity());
    }

    @Test
    public void testAddBookToLocationThrowsOnInvalidLocationId() {
        // Create new availability
        this.locationRepository.deleteById(MISSING_ID);
        final Book book = new Book(UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        this.bookRepository.save(book);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(MISSING_ID, book.getId(), DEFAULT_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform post request
        final ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                requestUrl,
                availabilityCreateDTO,
                String.class
        );

        // Assert that status code is right and exception is thrown
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        final String errorMessage = response.getBody();
        assertEquals("Location with id " + MISSING_ID + " doesn't exist.", errorMessage);
    }

    @Test
    public void testAddBookToLocationThrowsOnInvalidBookId() {
        // Create new availability
        final Location location = new Location(UPDATED_ADDRESS);
        this.locationRepository.save(location);
        this.bookRepository.deleteById(MISSING_ID);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), MISSING_ID, DEFAULT_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform post request
        final ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                requestUrl,
                availabilityCreateDTO,
                String.class
        );

        // Assert that status code is right and exception is thrown
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        final String errorMessage = response.getBody();
        assertEquals("Book with id " + MISSING_ID + " doesn't exist.", errorMessage);
    }

    @Test
    public void testAddBookToLocationThrowsOnNegativeQuantity() {
        // Create new availability
        final Location location = new Location(UPDATED_ADDRESS);
        this.locationRepository.save(location);
        final Book book = new Book(UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        this.bookRepository.save(book);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), book.getId(), -DEFAULT_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform post request
        final ResponseEntity<String> response = this.testRestTemplate.postForEntity(
                requestUrl,
                availabilityCreateDTO,
                String.class
        );

        // Assert that status code is right and exception is thrown
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        final String errorMessage = response.getBody();
        assertEquals("The provided quantity value is negative.", errorMessage);
    }

    @Test
    public void testChangeBookQuantity() {
        // Prepare data
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), book.getId(), UPDATED_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform put request
        final ResponseEntity<AvailabilityDTO> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.PUT,
                new HttpEntity<>(availabilityCreateDTO),
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Check that the data is saved
        final Availability updatedAvailability = this.availabilityRepository.findByLocationIdAndBookId(location.getId(), book.getId());
        assertEquals(location.getAddress(), updatedAvailability.getLocation().getAddress());
        assertEquals(book.getTitle(), updatedAvailability.getBook().getTitle());
        assertEquals(book.getAuthor(), updatedAvailability.getBook().getAuthor());
        assertEquals(book.getCategory(), updatedAvailability.getBook().getCategory());
        assertEquals(availabilityCreateDTO.getQuantity(), updatedAvailability.getQuantity());
    }

    @Test
    public void testChangeBookQuantityThrowsOnInvalidLocationId() {
        // Prepare data
        this.locationRepository.deleteById(MISSING_ID);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(MISSING_ID, book.getId(), UPDATED_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform put request
        final ResponseEntity<String> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.PUT,
                new HttpEntity<>(availabilityCreateDTO),
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and exception is thrown
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        final String errorMessage = response.getBody();
        assertEquals("The specified book is not available in the given location.", errorMessage);
    }

    @Test
    public void testChangeBookQuantityThrowsOnInvalidBookId() {
        // Prepare data
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        this.bookRepository.deleteById(MISSING_ID);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), MISSING_ID, UPDATED_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform put request
        final ResponseEntity<String> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.PUT,
                new HttpEntity<>(availabilityCreateDTO),
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and exception is thrown
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        final String errorMessage = response.getBody();
        assertEquals("The specified book is not available in the given location.", errorMessage);
    }

    @Test
    public void testChangeBookQuantityThrowsOnNegativeQuantity() {
        // Prepare data
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), book.getId(), -UPDATED_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform put request
        final ResponseEntity<String> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.PUT,
                new HttpEntity<>(availabilityCreateDTO),
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and exception is thrown
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        final String errorMessage = response.getBody();
        assertEquals("The provided quantity value is negative.", errorMessage);
    }

    @Test
    public void testRemoveBookAvailabilityFromLocation() {
        // Prepare data
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final String requestUrl = String.format("/api/availability/location/%d/book/%d", location.getId(), book.getId());

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
        assertNotNull(receivedId);
        assertEquals(book.getId(), receivedId);
    }

    @Test
    public void testRemoveBookAvailabilityFromLocationThrowsOnInvalidLocationId() {
        // Prepare data
        this.locationRepository.deleteById(MISSING_ID);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final String requestUrl = String.format("/api/availability/location/%d/book/%d", MISSING_ID, book.getId());

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
        assertEquals("The specified book is not available in the given location.", errorMessage);
    }

    @Test
    public void testRemoveBookAvailabilityFromLocationThrowsOnInvalidBookId() {
        // Prepare data
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        this.bookRepository.deleteById(MISSING_ID);
        final String requestUrl = String.format("/api/availability/location/%d/book/%d", location.getId(), MISSING_ID);

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
        assertEquals("The specified book is not available in the given location.", errorMessage);
    }
}
