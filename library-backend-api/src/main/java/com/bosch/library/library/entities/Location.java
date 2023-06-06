package com.bosch.library.library.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_generator")
    @SequenceGenerator(name = "location_generator", sequenceName = "location_sequence", allocationSize = 1)
    private Long id;
    private String address;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Availability> availabilities = new ArrayList<>();

    public Location() {
    }

    public Location(final String address) {
        this.address = address;
    }

    public Location(final Long id, final String address) {
        this(address);
        this.id = id;
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

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(final Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Availability> getAvailabilities() {
        return this.availabilities;
    }

    public void addAvailability(final Availability availability) {
        this.getAvailabilities().add(availability);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Location location = (Location) o;
        return this.address.equals(location.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.address);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + this.id +
                ", address='" + this.address + '\'' +
                ", supplier=" + this.supplier +
                ", availabilities=" + this.availabilities +
                '}';
    }
}
