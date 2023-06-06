package com.bosch.library.library.entities.dto;

import java.util.Objects;

public class LocationDTO {
    private Long id;
    private String address;

    public LocationDTO() {
    }

    public LocationDTO(final Long id, final String address) {
        this.id = id;
        this.address = address;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationDTO that = (LocationDTO) o;
        return this.address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.address);
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "id=" + this.id +
                ", address='" + this.address + '\'' +
                '}';
    }
}
