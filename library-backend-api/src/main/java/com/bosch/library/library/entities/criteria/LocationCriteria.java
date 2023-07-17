package com.bosch.library.library.entities.criteria;

import java.util.Objects;

public class LocationCriteria {
    private String address;

    private String supplierName;

    public LocationCriteria() {
    }

    public LocationCriteria(final String address, final String supplierName) {
        this.address = address;
        this.supplierName = supplierName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.address, this.supplierName);
    }

    public String getSupplier() {
        return this.supplierName;
    }

    public void setSupplier(final String supplierName) {
        this.supplierName = supplierName;
    }

    public LocationCriteria(final String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationCriteria that = (LocationCriteria) o;
        return Objects.equals(this.address, that.address) && Objects.equals(this.supplierName, that.supplierName);
    }

    @Override
    public String toString() {
        return "LocationCriteria{" +
                "address='" + this.address + '\'' +
                ", supplierName='" + this.supplierName + '\'' +
                '}';
    }
}
