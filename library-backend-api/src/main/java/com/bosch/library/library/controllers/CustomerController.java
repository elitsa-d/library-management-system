package com.bosch.library.library.controllers;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.entities.dto.CustomerCreateDTO;
import com.bosch.library.library.entities.dto.CustomerDTO;
import com.bosch.library.library.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Endpoint with Get mapping. Retrieves a list of customers.
     *
     * @return a list of customer DTOs
     */
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Get all customers", description = "This endpoint gives you a list of all customers and their book wishlist")
    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
        this.logger.info("Get all customers");
        return this.customerService.getAllCustomers();
    }

    /**
     * Endpoint with POST mapping. Creates a new customer using the information from the request body.
     *
     * @param customerCreateDTO the information for the customer to be created, provided in a CustomerCreateDTO object
     * @return response entity with the created customer DTO
     * or error message when the provided data is inaccurate
     */
    @PreAuthorize("hasRole('admin') or hasRole('customer')")
    @Operation(summary = "Create a new customer", description = "To add a customer it is required to provide first name and last name; biography is optional")
    @PostMapping("/customers")
    public CustomerDTO createCustomer(@Valid @RequestBody final CustomerCreateDTO customerCreateDTO) {
        this.logger.info("Save a new customer based on the following information: " + customerCreateDTO.toString());
        return this.customerService.createCustomer(customerCreateDTO);
    }

    /**
     * Endpoint with PATCH mapping. Updates a customer using the information from the request body.
     *
     * @param updatedCustomer the customer information that needs an update, provided in a CustomerDTO object
     * @return response entity with the updated customer DTO
     * or error message when the provided data is inaccurate
     */
    @PreAuthorize("hasRole('admin') or hasRole('customer')")
    @Operation(summary = "Edit an existing customer", description = "Provide the id of the customer to be edited and only the information that needs to be updated")
    @PatchMapping("/customers")
    public ResponseEntity<CustomerDTO> editCustomer(@RequestBody final CustomerDTO updatedCustomer) throws ElementNotFoundException {
        this.logger.info("Update customer based on the following information: " + updatedCustomer.toString());
        final CustomerDTO customerDTO = this.customerService.updateCustomer(updatedCustomer);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    /**
     * Endpoint with PUT mapping. Adds the book with the given id to the customer's wishlist.
     *
     * @param customerId the id of the customer whose wishlist is updated
     * @param bookId     the id of the book to be added
     * @return response entity with the updated customer DTO
     * or error message when the provided data is inaccurate
     */
    @PreAuthorize("hasRole('admin') or hasRole('customer')")
    @Operation(summary = "Add a book to the customer's wishlist", description = "Provide id of the customer and id of the book")
    @PutMapping("/customers/add-to-wishlist/{customerId}/{bookId}")
    public ResponseEntity<CustomerDTO> addBookToWishlist(@PathVariable final Long customerId, @PathVariable final Long bookId) throws ElementNotFoundException {
        this.logger.info("Update customer with id " + customerId + " by adding book with id " + bookId + " to their wishlist");
        final CustomerDTO customerDTO = this.customerService.addBookToWishlist(customerId, bookId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    /**
     * Endpoint with PUT mapping. Removes the book with the given id from the customer's wishlist.
     *
     * @param customerId the id of the customer whose wishlist is updated
     * @param bookId     the id of the book to be removed
     * @return response entity with the updated customer DTO
     * or error message when the provided data is inaccurate
     */
    @PreAuthorize("hasRole('admin') or hasRole('customer')")
    @Operation(summary = "Remove a book from the customer's wishlist", description = "Provide id of the customer and id of the book")
    @PutMapping("/customers/remove-from-wishlist/{customerId}/{bookId}")
    public ResponseEntity<CustomerDTO> removeBookFromWishlist(@PathVariable final Long customerId, @PathVariable final Long bookId) throws ElementNotFoundException {
        this.logger.info("Update customer with id " + customerId + " by removing book with id " + bookId + " from their wishlist");
        final CustomerDTO customerDTO = this.customerService.removeBookFromWishlist(customerId, bookId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    /**
     * Endpoint with DELETE mapping. Deletes the customer with the provided id.
     *
     * @param id the id of the customer to be deleted
     * @return response entity with the id of the deleted customer
     * or error message when the provided id is inaccurate
     */
    @PreAuthorize("hasRole('admin') or hasRole('customer')")
    @Operation(summary = "Delete a customer", description = "Provide the id of the customer that should be removed")
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Long> deleteCustomer(@PathVariable final Long id) throws ElementNotFoundException {
        this.logger.info("Delete customer with id " + id);
        final Long deletedCustomerId = this.customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(deletedCustomerId);
    }
}
