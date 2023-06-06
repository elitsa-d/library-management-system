package com.bosch.library.library.entities.dto;

import java.util.Objects;

public class AvailabilityByLocationDTO {
    private BookDTO book;
    private Integer quantity;

    public AvailabilityByLocationDTO() {
    }

    public AvailabilityByLocationDTO(final BookDTO book, final Integer quantity) {
        this.book = book;
        this.quantity = quantity;
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
        final AvailabilityByLocationDTO that = (AvailabilityByLocationDTO) o;
        return this.book.equals(that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.book);
    }

    @Override
    public String toString() {
        return "AvailabilityByLocationDTO{" +
                "book=" + this.book +
                ", quantity=" + this.quantity +
                '}';
    }
}
