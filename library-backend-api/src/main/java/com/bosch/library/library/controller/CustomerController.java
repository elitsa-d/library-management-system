package com.bosch.library.library.controller;

import com.bosch.library.library.entities.dto.CustomerCreateDTO;
import com.bosch.library.library.entities.dto.CustomerDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing customers.
 */
@Tag(description = "Access and modify information about the different customers", name = "Customers")
@RequestMapping("/api")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Endpoint with Get mapping. Retrieves a list of customers.
     *
     * @return a list of customer DTOs
     */
    @Operation(summary = "Get all customers", description = "This endpoint gives you a list of all customers and their book wishlist")
    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    /**
     * Endpoint with POST mapping. Creates a new customer using the information from the request body.
     *
     * @param customerCreateDTO the information for the customer to be created, provided in a CustomerCreateDTO object
     * @return response entity with the created customer DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Create a new customer", description = "To add a customer it is required to provide first name and last name; biography is optional")
    @PostMapping("/customers")
    public CustomerDTO createCustomer(@Valid @RequestBody final CustomerCreateDTO customerCreateDTO) {
        return this.customerService.createCustomer(customerCreateDTO);
    }

    /**
     * Endpoint with PATCH mapping. Updates a customer using the information from the request body.
     *
     * @param updatedCustomer the customer information that needs an update, provided in a CustomerDTO object
     * @return response entity with the updated customer DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Edit an existing customer", description = "Provide the id of the customer to be edited and only the information that needs to be updated")
    @PatchMapping("/customers")
    public ResponseEntity<?> editCustomer(@RequestBody final CustomerDTO updatedCustomer) {
        try {
            final CustomerDTO customerDTO = this.customerService.updateCustomer(updatedCustomer);
            return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint with PUT mapping. Adds the book with the given id to the customer's wishlist.
     *
     * @param customerId the id of the customer whose wishlist is updated
     * @param bookId     the id of the book to be added
     * @return response entity with the updated customer DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Add a book to the customer's wishlist", description = "Provide id of the customer and id of the book")
    @PutMapping("/customers/add-to-wishlist/{customerId}/{bookId}")
    public ResponseEntity<?> addBookToWishlist(@PathVariable final Long customerId, @PathVariable final Long bookId) {
        try {
            final CustomerDTO customerDTO = this.customerService.addBookToWishlist(customerId, bookId);
            return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint with PUT mapping. Removes the book with the given id from the customer's wishlist.
     *
     * @param customerId the id of the customer whose wishlist is updated
     * @param bookId     the id of the book to be removed
     * @return response entity with the updated customer DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Remove a book from the customer's wishlist", description = "Provide id of the customer and id of the book")
    @PutMapping("/customers/remove-from-wishlist/{customerId}/{bookId}")
    public ResponseEntity<?> removeBookFromWishlist(@PathVariable final Long customerId, @PathVariable final Long bookId) {
        try {
            final CustomerDTO customerDTO = this.customerService.removeBookFromWishlist(customerId, bookId);
            return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint with DELETE mapping. Deletes the customer with the provided id.
     *
     * @param id the id of the customer to be deleted
     * @return response entity with the id of the deleted customer
     * or error message when the provided id is inaccurate
     */
    @Operation(summary = "Delete a customer", description = "Provide the id of the customer that should be removed")
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
