package com.bosch.library.library.services;

import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.exceptions.ElementNotFoundException;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSuppliers();

    Supplier createSupplier(Supplier supplier);

    Supplier addNewLocation(Long supplierId, Long locationId) throws ElementNotFoundException;
}
