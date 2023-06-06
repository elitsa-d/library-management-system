package com.bosch.library.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence", allocationSize = 1)
    private Long id;
    private String title;
    private String author;
    private String category;
    private Long timesRented;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Availability> availabilities = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "wishlist")
    private final List<Customer> wishedBy = new ArrayList<>();

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

    public List<Availability> getAvailabilities() {
        return this.availabilities;
    }

    public void addAvailability(final Availability availability) {
        this.getAvailabilities().add(availability);
    }

    public List<Customer> getWishedBy() {
        return this.wishedBy;
    }

    public void addWishedBy(final Customer customer) {
        this.getWishedBy().add(customer);
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
        return this.title.equals(book.title) && this.author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + this.id +
                ", title='" + this.title + '\'' +
                ", author='" + this.author + '\'' +
                ", category='" + this.category + '\'' +
                ", timesRented=" + this.timesRented +
                ", availabilities=" + this.availabilities +
                ", wishedBy=" + this.wishedBy +
                '}';
    }
}

