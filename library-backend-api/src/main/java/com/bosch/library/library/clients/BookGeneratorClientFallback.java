package com.bosch.library.library.clients;

import com.bosch.library.library.controllers.errors.exceptions.ApiUnavailableException;
import com.bosch.library.library.entities.Book;
import org.springframework.stereotype.Component;


@Component
public class BookGeneratorClientFallback implements BookGeneratorClient {
    @Override
    public Book getGeneratedBook() throws ApiUnavailableException {
        throw new ApiUnavailableException("The service for generating books is currently unavailable.");
    }
}
