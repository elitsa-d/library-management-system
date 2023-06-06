package com.bosch.library.library.entities.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class CustomerCreateDTO {

    @NotNull(message = "The customer's first name should be specified.")
    private String firstName;

    @NotNull(message = "The customer's last name should be specified.")
    private String lastName;
    private String biography;

    public CustomerCreateDTO() {
    }

    public CustomerCreateDTO(final String firstName, final String lastName, final String biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerCreateDTO that = (CustomerCreateDTO) o;
        return this.firstName.equals(that.firstName) && this.lastName.equals(that.lastName) && Objects.equals(this.biography, that.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.biography);
    }

    @Override
    public String toString() {
        return "CustomerCreateDTO{" +
                "firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", biography='" + this.biography + '\'' +
                '}';
    }
}
