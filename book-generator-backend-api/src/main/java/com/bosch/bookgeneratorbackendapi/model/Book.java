package com.bosch.bookgeneratorbackendapi.model;

import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String category;

    public Book() {
    }

    public Book(final String title, final String author, final String category) {
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
        final Book book = (Book) o;
        return Objects.equals(this.title, book.title) && Objects.equals(this.author, book.author) && Objects.equals(this.category, book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.author, this.category);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + this.title + '\'' +
                ", author='" + this.author + '\'' +
                ", category='" + this.category + '\'' +
                '}';
    }
}
