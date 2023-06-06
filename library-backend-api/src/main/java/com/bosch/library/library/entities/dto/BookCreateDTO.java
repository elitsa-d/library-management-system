package com.bosch.library.library.entities.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookCreateDTO that = (BookCreateDTO) o;
        return this.title.equals(that.title) && this.author.equals(that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.author);
    }

    @Override
    public String toString() {
        return "BookCreateDTO{" +
                "title='" + this.title + '\'' +
                ", author='" + this.author + '\'' +
                ", category='" + this.category + '\'' +
                '}';
    }
}
