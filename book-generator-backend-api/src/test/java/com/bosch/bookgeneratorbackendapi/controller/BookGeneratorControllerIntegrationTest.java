package com.bosch.bookgeneratorbackendapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookGeneratorControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @WithMockUser(roles = {"admin"})
    @Test
    public void getGeneratedBook() throws Exception {
        // Prepare data
        final String requestUrl = "/books";

        // Perform get request
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.author").exists())
                .andExpect(jsonPath("$.category").exists());
    }
}
