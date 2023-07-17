package com.bosch.library.library.services;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.entities.criteria.CustomerCriteria;
import com.bosch.library.library.entities.dto.CustomerCreateDTO;
import com.bosch.library.library.entities.dto.CustomerDTO;

import java.util.List;

/**
 * Service interface for managing customers.
 */
public interface CustomerService {

    /**
     * Retrieves a list of customers.
     *
     * @return a list of customer DTOs
     */
    List<CustomerDTO> getAllCustomers(CustomerCriteria customerCriteria);

    /**
     * Creates a new customer using the provided information.
     *
     * @param customer the information for the customer to be created
     * @return the created customer DTO
     */
    CustomerDTO createCustomer(CustomerCreateDTO customer);

    /**
     * Updates a customer using the provided information.
     *
     * @param customer the customer information that needs an update
     * @return the updated customer DTO
     * @throws ElementNotFoundException if the customer with the provided id doesn't exist
     */
    CustomerDTO updateCustomer(CustomerDTO customer) throws ElementNotFoundException;

    /**
     * Adds the book with the given id to the customer's wishlist.
     *
     * @param customerId the id of the customer whose wishlist is updated
     * @param bookId     the id of the book to be added
     * @return the updated customer DTO
     * @throws ElementNotFoundException if the customer or the book with the provided id doesn't exist
     */
    CustomerDTO addBookToWishlist(Long customerId, Long bookId) throws ElementNotFoundException;

    /**
     * Removes the book with the given id from the customer's wishlist.
     *
     * @param customerId the id of the customer whose wishlist is updated
     * @param bookId     the id of the book to be removed
     * @return the updated customer DTO
     * @throws ElementNotFoundException if the customer or the book with the provided id doesn't exist
     */
    CustomerDTO removeBookFromWishlist(Long customerId, Long bookId) throws ElementNotFoundException;

    /**
     * Deletes the customer with the provided id.
     *
     * @param id the id of the customer to be deleted
     * @return the id of the deleted customer
     * @throws ElementNotFoundException if the customer with the provided id doesn't exist
     */
    Long deleteCustomer(Long id) throws ElementNotFoundException;
}
