package com.bosch.library.library.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private List<BookAvailability> bookAvailabilities = new ArrayList<>();

    public Location() {
    }

    public Location(final Long id, final String address) {
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

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(final Supplier supplier) {
        this.supplier = supplier;
    }

    public List<BookAvailability> getBookAvailabilities() {
        return this.bookAvailabilities;
    }

    public void addBookAvailability(final BookAvailability bookAvailability) {
        this.getBookAvailabilities().add(bookAvailability);
    }
}
