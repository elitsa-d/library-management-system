package com.bosch.library.library.controller;

import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.services.SupplierService;
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
    public List<Supplier> getAllSuppliers() {
        return this.supplierService.getAllSuppliers();
    }

    @PostMapping("/suppliers")
    public Supplier createSupplier(@RequestBody final Supplier supplier) {
        return this.supplierService.createSupplier(supplier);
    }

    @PutMapping("/suppliers/add-location/{supplierId}/{locationId}")
    public ResponseEntity<?> addNewLocation(@PathVariable final Long supplierId, @PathVariable final Long locationId) {
        try {
            final Supplier supplier = this.supplierService.addNewLocation(supplierId, locationId);
            return ResponseEntity.ok(supplier);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
