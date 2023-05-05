package com.bosch.library.library.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence", allocationSize = 1)
    private Long id;
    private String title;
    private String author;
    private String category;
    private Long timesRented;

    public Book() {
    }

    public Book(final String title, final String author, final String category) {
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public Book(final Long id, final String title, final String author, final String category) {
        this(title, author, category);
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public Long getTimesRented() {
        return this.timesRented;
    }

    public void setTimesRented(final Long timesRented) {
        this.timesRented = timesRented;
    }
}
