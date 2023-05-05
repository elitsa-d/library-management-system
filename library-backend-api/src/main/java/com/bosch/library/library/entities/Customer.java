package com.bosch.library.library.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator", sequenceName = "customer_sequence", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;

    public Customer() {
    }

    public Customer(final String firstName, final String lastName, final String biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
    }

    public Customer(final Long id, final String firstName, final String lastName, final String biography) {
        this(firstName, lastName, biography);
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getBiography() {
        return this.biography;
    }

    public void setBiography(final String biography) {
        this.biography = biography;
    }
}
