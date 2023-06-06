package com.bosch.library.library.entities.dto;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SupplierCreateDTO {

    @NotNull(message = "The supplier's name should be specified.")
    private String name;

    @NotNull(message = "The supplier's type should be specified.")
    private String type;
    private List<LocationCreateDTO> locationCreateDTOList = new ArrayList<>();

    public SupplierCreateDTO() {
    }

    public SupplierCreateDTO(final String name, final String type, final List<LocationCreateDTO> locationCreateDTOList) {
        this.name = name;
        this.type = type;
        this.locationCreateDTOList = locationCreateDTOList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<LocationCreateDTO> getLocations() {
        return this.locationCreateDTOList;
    }

    public void setLocations(final List<LocationCreateDTO> locationCreateDTOList) {
        this.locationCreateDTOList = locationCreateDTOList;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplierCreateDTO that = (SupplierCreateDTO) o;
        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        return "SupplierCreateDTO{" +
                "name='" + this.name + '\'' +
                ", type='" + this.type + '\'' +
                ", locationCreateDTOList=" + this.locationCreateDTOList +
                '}';
    }
}
