package com.bosch.library.library.entities.dto;

import jakarta.validation.constraints.NotNull;

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
}
