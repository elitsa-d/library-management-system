package com.bosch.library.library.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "supplier")
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Supplier supplier = (Supplier) o;
        return this.name.equals(supplier.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", type='" + this.type + '\'' +
                ", locations=" + this.locations +
                ", rents=" + this.rents +
                '}';
    }
}
