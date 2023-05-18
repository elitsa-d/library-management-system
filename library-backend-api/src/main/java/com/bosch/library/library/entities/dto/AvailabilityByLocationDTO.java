package com.bosch.library.library.entities.dto;

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
}
