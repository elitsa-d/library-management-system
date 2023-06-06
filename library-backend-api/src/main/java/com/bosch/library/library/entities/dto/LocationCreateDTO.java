package com.bosch.library.library.entities.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class LocationCreateDTO {

    @NotNull(message = "The location's address should be specified.")
    private String address;

    @NotNull(message = "The supplier owning this location should be specified.")
    private Long supplierId;

    public LocationCreateDTO() {
    }

    public LocationCreateDTO(final String address, final Long supplierId) {
        this.address = address;
        this.supplierId = supplierId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(final Long supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationCreateDTO that = (LocationCreateDTO) o;
        return this.address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.address);
    }

    @Override
    public String toString() {
        return "LocationCreateDTO{" +
                "address='" + this.address + '\'' +
                ", supplierId=" + this.supplierId +
                '}';
    }
}
