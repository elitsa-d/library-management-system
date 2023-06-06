package com.bosch.library.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "location_book_availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_generator")
    @SequenceGenerator(name = "availability_generator", sequenceName = "availability_sequence", allocationSize = 1)
    private Long id;

    @JsonIgnoreProperties(value = "bookAvailabilities")
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @JsonIgnoreProperties(value = "bookAvailabilities")
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
    private Integer quantity;

    public Availability() {
    }

    public Availability(final Location location, final Book book, final Integer quantity) {
        this.location = location;
        this.book = book;
        this.quantity = quantity;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(final Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Availability that = (Availability) o;
        return this.location.equals(that.location) && this.book.equals(that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.location, this.book);
    }

    @Override
    public String toString() {
        return "Availability{" +
                "id=" + this.id +
                ", location=" + this.location +
                ", book=" + this.book +
                ", quantity=" + this.quantity +
                '}';
    }
}
