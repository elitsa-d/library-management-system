package com.bosch.library.library.controller;

import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody final Customer customer) {
        return this.customerService.createCustomer(customer);
    }

    @PutMapping("/customers")
    public ResponseEntity<?> editCustomer(@RequestBody final Customer updatedCustomer) {
        try {
            final Customer customer = this.customerService.updateCustomer(updatedCustomer);
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable final Long id) {
        try {
            final Long deletedCustomerId = this.customerService.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedCustomerId);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
