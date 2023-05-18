package com.bosch.library.library.entities.dto;

public class AvailabilityByBookDTO {
    private LocationDTO location;
    private Integer quantity;

    public AvailabilityByBookDTO() {
    }

    public AvailabilityByBookDTO(final LocationDTO location, final Integer quantity) {
        this.location = location;
        this.quantity = quantity;
    }

    public LocationDTO getLocation() {
        return this.location;
    }

    public void setLocation(final LocationDTO location) {
        this.location = location;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }
}
