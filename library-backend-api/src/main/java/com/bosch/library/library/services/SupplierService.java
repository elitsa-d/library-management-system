package com.bosch.library.library.services;

import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.exceptions.ElementNotFoundException;

import java.util.List;

public interface SupplierService {
    public List<Supplier> getAllSuppliers();

    public Supplier createSupplier(Supplier supplier);

    public Supplier addNewLocation(Long supplierId, Long locationId) throws ElementNotFoundException;
}
