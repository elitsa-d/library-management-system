package com.bosch.library.library.services;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.controllers.errors.exceptions.ValidationException;
import com.bosch.library.library.entities.criteria.SupplierCriteria;
import com.bosch.library.library.entities.dto.SupplierCreateDTO;
import com.bosch.library.library.entities.dto.SupplierDTO;

import java.util.List;

/**
 * Service interface for managing suppliers.
 */
public interface SupplierService {

    /**
     * Retrieves a list of suppliers including the locations they own.
     *
     * @return a list of supplier DTOs
     */
    List<SupplierDTO> getAllSuppliers(SupplierCriteria supplierCriteria);

    /**
     * Creates a new supplier using the provided information.
     *
     * @param supplier the information for the supplier to be created including optional list of owned locations
     * @return the created supplier DTO
     * @throws ValidationException if a supplier with the provided name already exists
     *                             or if a provided location is already owned by another supplier
     */
    SupplierDTO createSupplier(SupplierCreateDTO supplier) throws ValidationException;

    /**
     * Changes the location's owner to be the supplier with the provided id.
     *
     * @param supplierId the id of the supplier who is the new owner
     * @param locationId the id of the location whose owner is updated
     * @return the updated supplier DTO
     * @throws ElementNotFoundException if the supplier or the location with the provided id doesn't exist
     */
    SupplierDTO addNewLocation(Long supplierId, Long locationId) throws ElementNotFoundException;
}
