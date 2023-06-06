package com.bosch.library.library.entities.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AvailabilityByBookDTO that = (AvailabilityByBookDTO) o;
        return this.location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.location);
    }

    @Override
    public String toString() {
        return "AvailabilityByBookDTO{" +
                "location=" + this.location +
                ", quantity=" + this.quantity +
                '}';
    }
}
