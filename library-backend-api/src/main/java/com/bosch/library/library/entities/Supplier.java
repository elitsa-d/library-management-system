package com.bosch.library.library.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_generator")
    @SequenceGenerator(name = "supplier_generator", sequenceName = "supplier_sequence", allocationSize = 1)
    private Long id;
    private String name;
    private String type;

    @JsonManagedReference
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private final List<Location> locations = new ArrayList<>();
    private Integer rents;

    public Supplier() {
    }

    public Supplier(final Long id, final String name, final String type, final Integer rents) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rents = rents;
    }

    public List<Location> getLocations() {
        return this.locations;
    }


    public void addLocation(final Location location) {
        location.setSupplier(this);
        this.getLocations().add(location);
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

    public Integer getRents() {
        return this.rents;
    }

    public void setRents(final Integer rents) {
        this.rents = rents;
    }
}
