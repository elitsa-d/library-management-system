package com.bosch.library.library.entities.dto;

import jakarta.validation.constraints.NotNull;

public class AvailabilityCreateDTO {
    @NotNull(message = "The location id should be specified.")
    private Long locationId;

    @NotNull(message = "The book id should be specified.")
    private Long bookId;

    @NotNull(message = "The quantity should be specified.")
    private Integer quantity;

    public AvailabilityCreateDTO() {
    }

    public AvailabilityCreateDTO(final Long locationId, final Long bookId, final Integer quantity) {
        this.locationId = locationId;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public Long getBookId() {
        return this.bookId;
    }

    public void setBookId(final Long bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }
}
