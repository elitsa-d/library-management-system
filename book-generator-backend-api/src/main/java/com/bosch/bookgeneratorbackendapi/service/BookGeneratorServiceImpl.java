package com.bosch.bookgeneratorbackendapi.service;

import com.bosch.bookgeneratorbackendapi.model.Book;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

@Service
public class BookGeneratorServiceImpl implements BookGeneratorService {

    private final Faker faker;

    public BookGeneratorServiceImpl(final Faker faker) {
        this.faker = faker;
    }

    @Override
    public Book generateBook() {
        return new Book(this.faker.book().title(), this.faker.book().author(), this.faker.book().genre());
    }
}
