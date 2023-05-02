package com.bosch.library.library.services;

import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.exceptions.InvalidLocationIdException;
import com.bosch.library.library.exceptions.InvalidSupplierIdException;

import java.util.List;

public interface SupplierService {
    public List<Supplier> getAllSuppliers();

    public Supplier createSupplier(Supplier supplier);

    public Supplier addNewLocation(Long supplierId, Long locationId) throws InvalidLocationIdException, InvalidSupplierIdException;
}
