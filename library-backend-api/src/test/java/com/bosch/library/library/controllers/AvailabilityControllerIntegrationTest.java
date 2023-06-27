package com.bosch.library.library.controllers;

import com.bosch.library.library.entities.Availability;
import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.AvailabilityCreateDTO;
import com.bosch.library.library.repositories.AvailabilityRepository;
import com.bosch.library.library.repositories.BookRepository;
import com.bosch.library.library.repositories.LocationRepository;
import com.bosch.library.library.repositories.specifications.BookSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AvailabilityControllerIntegrationTest {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

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

    @BeforeEach
    public void setUp() {
        final Location location = new Location(DEFAULT_ADDRESS);
        this.locationRepository.save(location);

        final Book book = new Book(DEFAULT_TITLE, DEFAULT_AUTHOR, DEFAULT_CATEGORY);
        this.bookRepository.save(book);

        final Availability availability = new Availability(location, book, DEFAULT_QUANTITY);
        this.availabilityRepository.save(availability);
    }

    @AfterEach
    public void emptyData() {
        this.availabilityRepository.deleteAll();
        this.locationRepository.deleteAll();
        this.bookRepository.deleteAll();
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAvailableBooksInLocation() throws Exception {
        // Prepare data
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final String requestUrl = String.format("/api/availability/books-in-location/%d", location.getId());

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].book.title").value(DEFAULT_TITLE))
                .andExpect(jsonPath("$[0].book.author").value(DEFAULT_AUTHOR))
                .andExpect(jsonPath("$[0].book.category").value(DEFAULT_CATEGORY))
                .andExpect(jsonPath("$[0].quantity").value(DEFAULT_QUANTITY));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetLocationsWithAvailableBook() throws Exception {
        // Prepare data
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final String requestUrl = String.format("/api/availability/locations-having-book/%d", book.getId());

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].location.address").value(DEFAULT_ADDRESS))
                .andExpect(jsonPath("$[0].quantity").value(DEFAULT_QUANTITY));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testAddBookToLocation() throws Exception {
        // Create new availability
        final ObjectMapper mapper = new ObjectMapper();
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final Book book = new Book(UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        this.bookRepository.save(book);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), book.getId(), DEFAULT_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform post request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(requestUrl)
                        .content(mapper.writeValueAsString(availabilityCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Check that the data is saved
        final Availability savedAvailability = this.availabilityRepository.findByLocationIdAndBookId(location.getId(), book.getId());
        assertEquals(book.getTitle(), savedAvailability.getBook().getTitle());
        assertEquals(book.getAuthor(), savedAvailability.getBook().getAuthor());
        assertEquals(book.getCategory(), savedAvailability.getBook().getCategory());
        assertEquals(location.getAddress(), savedAvailability.getLocation().getAddress());
        assertEquals(availabilityCreateDTO.getQuantity(), savedAvailability.getQuantity());
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testAddBookToLocationThrowsOnInvalidLocationId() throws Exception {
        // Create new availability
        final ObjectMapper mapper = new ObjectMapper();
        this.locationRepository.deleteById(MISSING_ID);
        final Book book = new Book(UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        this.bookRepository.save(book);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(MISSING_ID, book.getId(), DEFAULT_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform post request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(requestUrl)
                        .content(mapper.writeValueAsString(availabilityCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Location with id " + MISSING_ID + " doesn't exist."));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testAddBookToLocationThrowsOnInvalidBookId() throws Exception {
        // Create new availability
        final ObjectMapper mapper = new ObjectMapper();
        final Location location = new Location(UPDATED_ADDRESS);
        this.locationRepository.save(location);
        this.bookRepository.deleteById(MISSING_ID);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), MISSING_ID, DEFAULT_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform post request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(requestUrl)
                        .content(mapper.writeValueAsString(availabilityCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book with id " + MISSING_ID + " doesn't exist."));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testAddBookToLocationThrowsOnNegativeQuantity() throws Exception {
        // Create new availability
        final ObjectMapper mapper = new ObjectMapper();
        final Location location = new Location(UPDATED_ADDRESS);
        this.locationRepository.save(location);
        final Book book = new Book(UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        this.bookRepository.save(book);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), book.getId(), -DEFAULT_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform post request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(requestUrl)
                        .content(mapper.writeValueAsString(availabilityCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The provided quantity value is negative."));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testChangeBookQuantity() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), book.getId(), UPDATED_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform put request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(requestUrl)
                        .content(mapper.writeValueAsString(availabilityCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Check that the data is saved
        final Availability updatedAvailability = this.availabilityRepository.findByLocationIdAndBookId(location.getId(), book.getId());
        assertEquals(location.getAddress(), updatedAvailability.getLocation().getAddress());
        assertEquals(book.getTitle(), updatedAvailability.getBook().getTitle());
        assertEquals(book.getAuthor(), updatedAvailability.getBook().getAuthor());
        assertEquals(book.getCategory(), updatedAvailability.getBook().getCategory());
        assertEquals(availabilityCreateDTO.getQuantity(), updatedAvailability.getQuantity());
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testChangeBookQuantityThrowsOnInvalidLocationId() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        this.locationRepository.deleteById(MISSING_ID);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(MISSING_ID, book.getId(), UPDATED_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform put request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(requestUrl)
                        .content(mapper.writeValueAsString(availabilityCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The specified book is not available in the given location."));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testChangeBookQuantityThrowsOnInvalidBookId() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        this.bookRepository.deleteById(MISSING_ID);
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), MISSING_ID, UPDATED_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform put request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(requestUrl)
                        .content(mapper.writeValueAsString(availabilityCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The specified book is not available in the given location."));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testChangeBookQuantityThrowsOnNegativeQuantity() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final AvailabilityCreateDTO availabilityCreateDTO = new AvailabilityCreateDTO(location.getId(), book.getId(), -UPDATED_QUANTITY);
        final String requestUrl = "/api/availability";

        // Perform put request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(requestUrl)
                        .content(mapper.writeValueAsString(availabilityCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The provided quantity value is negative."));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testRemoveBookAvailabilityFromLocation() throws Exception {
        // Prepare data
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final String requestUrl = String.format("/api/availability/location/%d/book/%d", location.getId(), book.getId());

        // Perform delete request
        final MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(requestUrl))
                .andExpect(status().isOk())
                .andReturn();

        // Assert that the right data is returned
        assertEquals(result.getResponse().getContentAsString(), book.getId().toString());
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testRemoveBookAvailabilityFromLocationThrowsOnInvalidLocationId() throws Exception {
        // Prepare data
        this.locationRepository.deleteById(MISSING_ID);
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final String requestUrl = String.format("/api/availability/location/%d/book/%d", MISSING_ID, book.getId());

        // Perform delete request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(requestUrl))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The specified book is not available in the given location."));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testRemoveBookAvailabilityFromLocationThrowsOnInvalidBookId() throws Exception {
        // Prepare data
        final Location location = this.locationRepository.findLocationByAddress(DEFAULT_ADDRESS);
        this.bookRepository.deleteById(MISSING_ID);
        final String requestUrl = String.format("/api/availability/location/%d/book/%d", location.getId(), MISSING_ID);

        // Perform delete request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(requestUrl))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The specified book is not available in the given location."));
    }
}
