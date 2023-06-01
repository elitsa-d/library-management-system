package com.bosch.library.library.controller;

import com.bosch.library.library.entities.dto.SupplierCreateDTO;
import com.bosch.library.library.entities.dto.SupplierDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.services.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing suppliers.
 */
@Tag(description = "Access and modify information about the different suppliers", name = "Suppliers")
@RequestMapping("/api")
@RestController
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(final SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * Endpoint with GET mapping. Retrieves a list of suppliers including the locations they own.
     *
     * @return a list of supplier DTOs
     */
    @Operation(summary = "Get all suppliers", description = "This endpoint gives you a list of all suppliers and the locations they own")
    @GetMapping("/suppliers")
    public List<SupplierDTO> getAllSuppliers() {
        return this.supplierService.getAllSuppliers();
    }

    /**
     * Endpoint with POST mapping. Creates a new supplier using the information from the request body.
     *
     * @param supplierCreateDTO the information for the supplier to be created, including optional list of owned locations
     * @return response entity with the created supplier DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Create a new supplier", description = "To add a supplier it is required to provide the supplier's name and type; providing a list of owned locations is optional")
    @PostMapping("/suppliers")
    public ResponseEntity<?> createSupplier(@Valid @RequestBody final SupplierCreateDTO supplierCreateDTO) {
        try {
            final SupplierDTO supplierDTO = this.supplierService.createSupplier(supplierCreateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(supplierDTO);
        } catch (final ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint with PUT mapping. Changes the location's owner to be the supplier with the provided id.
     *
     * @param supplierId the id of the supplier who is the new owner
     * @param locationId the id of the location whose owner is updated
     * @return response entity with the updated supplier DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Change the supplier who owns a given location", description = "Provide supplier id and location id to change the supplier who owns the given location")
    @PutMapping("/suppliers/add-location/{supplierId}/{locationId}")
    public ResponseEntity<?> addNewLocation(@PathVariable final Long supplierId, @PathVariable final Long locationId) {
        try {
            final SupplierDTO supplierDTO = this.supplierService.addNewLocation(supplierId, locationId);
            return ResponseEntity.ok(supplierDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
