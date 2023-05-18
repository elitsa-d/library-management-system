package com.bosch.library.library.entities.dto;

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
}
