package com.bosch.bookgeneratorbackendapi.controller;

import com.bosch.bookgeneratorbackendapi.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookGeneratorControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getGeneratedBook() {
        // Prepare data
        final String requestUrl = "/books";

        // Perform get request
        final ResponseEntity<Book> response = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Assert that status code is right and data is retrieved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final Book book = response.getBody();
        assertNotNull(book);
        assertNotNull(book.getTitle());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getCategory());
    }
}
