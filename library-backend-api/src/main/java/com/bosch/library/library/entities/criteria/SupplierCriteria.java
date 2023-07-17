package com.bosch.library.library.entities.criteria;

import java.util.Objects;

public class SupplierCriteria {
    private String name;
    private String type;

    public SupplierCriteria() {
    }

    public SupplierCriteria(final String name, final String type) {
        this.name = name;
        this.type = type;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplierCriteria that = (SupplierCriteria) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.type);
    }

    @Override
    public String toString() {
        return "SupplierCriteria{" +
                "name='" + this.name + '\'' +
                ", type='" + this.type + '\'' +
                '}';
    }
}
