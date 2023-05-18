package com.bosch.library.library.controller;

import com.bosch.library.library.entities.dto.SupplierCreateDTO;
import com.bosch.library.library.entities.dto.SupplierDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.services.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(final SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public List<SupplierDTO> getAllSuppliers() {
        return this.supplierService.getAllSuppliers();
    }

    @PostMapping("/suppliers")
    public ResponseEntity<?> createSupplier(@Valid @RequestBody final SupplierCreateDTO supplierCreateDTO) {
        try {
            final SupplierDTO supplierDTO = this.supplierService.createSupplier(supplierCreateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(supplierDTO);
        } catch (final ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

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
