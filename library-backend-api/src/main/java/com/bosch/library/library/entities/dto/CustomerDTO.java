package com.bosch.library.library.entities.dto;

import java.util.List;
import java.util.Objects;

public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
    private List<BookDTO> wishlist;

    public CustomerDTO() {
    }

    public CustomerDTO(final Long id, final String firstName, final String lastName, final String biography) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
    }

    public CustomerDTO(final Long id, final String firstName, final String lastName, final String biography, final List<BookDTO> wishlist) {
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

    public List<BookDTO> getWishlist() {
        return this.wishlist;
    }

    public void setWishlist(final List<BookDTO> wishlist) {
        this.wishlist = wishlist;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerDTO that = (CustomerDTO) o;
        return this.firstName.equals(that.firstName) && this.lastName.equals(that.lastName) && Objects.equals(this.biography, that.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.biography);
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + this.id +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", biography='" + this.biography + '\'' +
                ", wishlist=" + this.wishlist +
                '}';
    }
}
