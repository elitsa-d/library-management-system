package com.bosch.library.library.services;

import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.exceptions.ElementNotFoundException;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer) throws ElementNotFoundException;

    Customer addBookToWishlist(Long customerId, Long bookId) throws ElementNotFoundException;

    Customer removeBookFromWishlist(Long customerId, Long bookId) throws ElementNotFoundException;

    Long deleteCustomer(Long id) throws ElementNotFoundException;
}
