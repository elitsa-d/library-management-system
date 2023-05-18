package com.bosch.library.library.services;

import com.bosch.library.library.entities.dto.SupplierCreateDTO;
import com.bosch.library.library.entities.dto.SupplierDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;

import java.util.List;

public interface SupplierService {
    List<SupplierDTO> getAllSuppliers();

    SupplierDTO createSupplier(SupplierCreateDTO supplier) throws ValidationException;

    SupplierDTO addNewLocation(Long supplierId, Long locationId) throws ElementNotFoundException;
}
