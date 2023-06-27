package com.bosch.library.library.controllers;

import com.bosch.library.library.entities.Availability;
import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.criteria.BookCriteria;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import com.bosch.library.library.entities.dto.BookDTO;
import com.bosch.library.library.repositories.AvailabilityRepository;
import com.bosch.library.library.repositories.BookRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final String DEFAULT_TITLE = "Gone with the Wind";
    private static final String DEFAULT_AUTHOR = "Margaret Mitchell";
    private static final String DEFAULT_CATEGORY = "Novel";
    private static final String UPDATED_TITLE = "I'd Die for You and Other Lost Stories";
    private static final String UPDATED_AUTHOR = "F. Scott Fitzgerald";
    private static final String UPDATED_CATEGORY = "Short stories";
    private static final Long MISSING_ID = 15L;

    @BeforeEach
    public void setUp() {
        final Book book1 = new Book(DEFAULT_TITLE, DEFAULT_AUTHOR, DEFAULT_CATEGORY);
        final Book book2 = new Book("Wuthering Heights", "Emily Brontë", "Novel");
        this.bookRepository.saveAll(List.of(book1, book2));

        this.availabilityRepository.save(new Availability(null, book1, 10));
    }

    @AfterEach
    public void emptyData() {
        this.bookRepository.deleteAll();
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAllBooks() throws Exception {
        // Prepare data
        final String requestUrl = "/api/books";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value(DEFAULT_TITLE))
                .andExpect(jsonPath("$[0].author").value(DEFAULT_AUTHOR))
                .andExpect(jsonPath("$[0].category").value(DEFAULT_CATEGORY));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAllBooksBySpecificCategory() throws Exception {
        // Prepare data
        final String requestUrl = "/api/books?category=Novel";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value(DEFAULT_TITLE))
                .andExpect(jsonPath("$[0].author").value(DEFAULT_AUTHOR))
                .andExpect(jsonPath("$[0].category").value(DEFAULT_CATEGORY));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAllBooksBySpecificTitleAndAuthor() throws Exception {
        // Prepare data
        final String requestUrl = "/api/books?title=Gone&author=Margaret";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value(DEFAULT_TITLE))
                .andExpect(jsonPath("$[0].author").value(DEFAULT_AUTHOR))
                .andExpect(jsonPath("$[0].category").value(DEFAULT_CATEGORY));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAllBooksBySpecificTitleAuthorAndCategory() throws Exception {
        // Prepare data
        final String requestUrl = "/api/books?title=Gone&author=Margaret&category=Novel";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value(DEFAULT_TITLE))
                .andExpect(jsonPath("$[0].author").value(DEFAULT_AUTHOR))
                .andExpect(jsonPath("$[0].category").value(DEFAULT_CATEGORY));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAllBooksBySpecificTitleAuthorCategoryAndQuantity() throws Exception {
        // Prepare data
        final String requestUrl = "/api/books?title=Gone&author=Margaret&category=Novel&quantity=10";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value(DEFAULT_TITLE))
                .andExpect(jsonPath("$[0].author").value(DEFAULT_AUTHOR))
                .andExpect(jsonPath("$[0].category").value(DEFAULT_CATEGORY));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testGetAllBooksByInvalidQuantityReturnsEmptyList() throws Exception {
        // Prepare data
        final String requestUrl = "/api/books?quantity=100";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testCreateBook() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        final BookCreateDTO bookCreateDTO = new BookCreateDTO(UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        final String requestUrl = "/api/books";

        // Perform post request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(requestUrl)
                        .content(mapper.writeValueAsString(bookCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Check that the data is saved
        final List<Book> savedBook = this.bookRepository.findAll(BookSpecification.hasCriteria(new BookCriteria(UPDATED_TITLE, UPDATED_AUTHOR, null, null)));
        assertNotNull(savedBook);
        assertEquals(bookCreateDTO.getTitle(), savedBook.get(0).getTitle());
        assertEquals(bookCreateDTO.getAuthor(), savedBook.get(0).getAuthor());
        assertEquals(bookCreateDTO.getCategory(), savedBook.get(0).getCategory());
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testEditBook() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final BookDTO bookDTO = new BookDTO(book.getId(), UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        final String requestUrl = "/api/books";

        // Perform patch request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .patch(requestUrl)
                        .content(mapper.writeValueAsString(bookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testEditBookThrowsOnInvalidBookId() throws Exception {
        // Prepare data
        final ObjectMapper mapper = new ObjectMapper();
        this.bookRepository.deleteById(MISSING_ID);
        final BookDTO bookDTO = new BookDTO(MISSING_ID, UPDATED_TITLE, UPDATED_AUTHOR, UPDATED_CATEGORY);
        final String requestUrl = "/api/books";

        // Perform patch request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .patch(requestUrl)
                        .content(mapper.writeValueAsString(bookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book with id " + MISSING_ID + " doesn't exist."));
    }

    @WithMockUser(roles = {"admin"})
    @Test
    public void testDeleteBook() throws Exception {
        // Prepare data
        final Book book = this.bookRepository.findOne(BookSpecification.hasCriteria(new BookCriteria(DEFAULT_TITLE, DEFAULT_AUTHOR, null, null))).get();
        final String requestUrl = String.format("/api/books/%d", book.getId());

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
    public void testDeleteBookThrowsOnInvalidBookId() throws Exception {
        // Prepare data
        this.bookRepository.deleteById(MISSING_ID);
        final String requestUrl = String.format("/api/books/%d", MISSING_ID);

        // Perform delete request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(requestUrl))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book with id " + MISSING_ID + " doesn't exist."));
    }
}
