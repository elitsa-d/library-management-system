package com.bosch.library.library.entities.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SupplierDTO {
    private Long id;
    private String name;
    private String type;
    private List<LocationDTO> locationDTOList = new ArrayList<>();

    public SupplierDTO() {
    }

    public SupplierDTO(final Long id, final String name, final String type, final List<LocationDTO> locationDTOList) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.locationDTOList = locationDTOList;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public List<LocationDTO> getLocations() {
        return this.locationDTOList;
    }

    public void setLocations(final List<LocationDTO> locationDTOList) {
        this.locationDTOList = locationDTOList;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplierDTO that = (SupplierDTO) o;
        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
