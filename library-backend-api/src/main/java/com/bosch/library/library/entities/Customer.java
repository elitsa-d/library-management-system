package com.bosch.library.library.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator", sequenceName = "customer_sequence", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;

    @ManyToMany
    @JoinTable(
            name = "customer_wishlist",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> wishlist = new ArrayList<>();

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

    public Customer(final Long id, final String firstName, final String lastName, final String biography, final List<Book> wishlist) {
        this(id, firstName, lastName, biography);
        this.wishlist = wishlist;
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

    public List<Book> getWishlist() {
        return this.wishlist;
    }

    public void addBookToWishlist(final Book book) {
        this.getWishlist().add(book);
    }

    public void removeBookFromWishlist(final Book book) {
        this.getWishlist().remove(book);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Customer customer = (Customer) o;
        return this.firstName.equals(customer.firstName) && this.lastName.equals(customer.lastName) && Objects.equals(this.biography, customer.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.biography);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + this.id +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", biography='" + this.biography + '\'' +
                ", wishlist=" + this.wishlist +
                '}';
    }
}
