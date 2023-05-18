package com.bosch.library.library.entities.dto;

import jakarta.validation.constraints.NotNull;

public class BookCreateDTO {

    @NotNull(message = "The book's title should be specified.")
    private String title;

    @NotNull(message = "The book's author should be specified.")
    private String author;

    @NotNull(message = "The book's category should be specified.")
    private String category;

    public BookCreateDTO() {
    }

    public BookCreateDTO(final String title, final String author, final String category) {
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }
}
