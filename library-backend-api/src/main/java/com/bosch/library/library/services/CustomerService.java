package com.bosch.library.library.services;

import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.exceptions.ElementNotFoundException;

import java.util.List;

public interface CustomerService {

    public List<Customer> getAllCustomers();

    public Customer createCustomer(Customer customer);

    public Customer updateCustomer(Customer customer) throws ElementNotFoundException;

    public Long deleteCustomer(Long id) throws ElementNotFoundException;
}
