package com.bosch.library.library.controller;

import com.bosch.library.library.entities.dto.CustomerCreateDTO;
import com.bosch.library.library.entities.dto.CustomerDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.services.CustomerService;
import jakarta.validation.Valid;
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
    public List<CustomerDTO> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    @PostMapping("/customers")
    public CustomerDTO createCustomer(@Valid @RequestBody final CustomerCreateDTO customerCreateDTO) {
        return this.customerService.createCustomer(customerCreateDTO);
    }

    @PatchMapping("/customers")
    public ResponseEntity<?> editCustomer(@RequestBody final CustomerDTO updatedCustomer) {
        try {
            final CustomerDTO customerDTO = this.customerService.updateCustomer(updatedCustomer);
            return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/customers/add-to-wishlist/{customerId}/{bookId}")
    public ResponseEntity<?> addBookToWishlist(@PathVariable final Long customerId, @PathVariable final Long bookId) {
        try {
            final CustomerDTO customerDTO = this.customerService.addBookToWishlist(customerId, bookId);
            return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/customers/remove-from-wishlist/{customerId}/{bookId}")
    public ResponseEntity<?> removeBookFromWishlist(@PathVariable final Long customerId, @PathVariable final Long bookId) {
        try {
            final CustomerDTO customerDTO = this.customerService.removeBookFromWishlist(customerId, bookId);
            return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
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
