package com.bosch.library.library.entities.dto;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

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
}
