package com.bosch.library.library.services;

import com.bosch.library.library.entities.dto.CustomerCreateDTO;
import com.bosch.library.library.entities.dto.CustomerDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO createCustomer(CustomerCreateDTO customer);

    CustomerDTO updateCustomer(CustomerDTO customer) throws ElementNotFoundException;

    CustomerDTO addBookToWishlist(Long customerId, Long bookId) throws ElementNotFoundException;

    CustomerDTO removeBookFromWishlist(Long customerId, Long bookId) throws ElementNotFoundException;

    Long deleteCustomer(Long id) throws ElementNotFoundException;
}
