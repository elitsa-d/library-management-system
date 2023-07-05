package com.bosch.library.library.clients;

import com.bosch.library.library.controllers.errors.exceptions.ApiUnavailableException;
import com.bosch.library.library.entities.Book;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "book-generator-backend-api",
        url = "http://localhost:9090",
        fallback = BookGeneratorClientFallback.class
)
public interface BookGeneratorClient {
    @Headers("Content-Type: application/json")
    @RequestMapping(method = RequestMethod.GET, value = "/books")
    Book getGeneratedBook() throws ApiUnavailableException;
}