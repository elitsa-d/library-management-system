package com.bosch.library.library.services;

import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(final Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(final Customer updatedCustomer) throws ElementNotFoundException {
        final Long id = updatedCustomer.getId();

        final Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Customer with id " + id + " doesn't exist."));

        if (updatedCustomer.getFirstName() != null) {
            customer.setFirstName(updatedCustomer.getFirstName());
        }

        if (updatedCustomer.getLastName() != null) {
            customer.setLastName(updatedCustomer.getLastName());
        }

        if (updatedCustomer.getBiography() != null) {
            customer.setBiography(updatedCustomer.getBiography());
        }

        return this.customerRepository.save(customer);
    }

    @Override
    public Long deleteCustomer(final Long id) throws ElementNotFoundException {
        final Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Customer with id " + id + " doesn't exist."));

        this.customerRepository.delete(customer);
        return id;
    }
}
