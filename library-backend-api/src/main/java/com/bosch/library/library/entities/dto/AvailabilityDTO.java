package com.bosch.library.library.entities.dto;

import java.util.Objects;

public class AvailabilityDTO {
    private Long id;
    private LocationDTO location;
    private BookDTO book;
    private Integer quantity;

    public AvailabilityDTO() {
    }

    public AvailabilityDTO(final Long id, final LocationDTO location, final BookDTO book, final Integer quantity) {
        this.id = id;
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

    public LocationDTO getLocation() {
        return this.location;
    }

    public void setLocation(final LocationDTO location) {
        this.location = location;
    }

    public BookDTO getBook() {
        return this.book;
    }

    public void setBook(final BookDTO book) {
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
        final AvailabilityDTO that = (AvailabilityDTO) o;
        return this.location.equals(that.location) && this.book.equals(that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.location, this.book);
    }

    @Override
    public String toString() {
        return "AvailabilityDTO{" +
                "id=" + this.id +
                ", location=" + this.location +
                ", book=" + this.book +
                ", quantity=" + this.quantity +
                '}';
    }
}
