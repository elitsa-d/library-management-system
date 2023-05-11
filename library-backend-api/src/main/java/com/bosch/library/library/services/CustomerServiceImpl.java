package com.bosch.library.library.services;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.repositories.BookRepository;
import com.bosch.library.library.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    public CustomerServiceImpl(final CustomerRepository customerRepository, final BookRepository bookRepository) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
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
    public Customer addBookToWishlist(final Long customerId, final Long bookId) throws ElementNotFoundException {
        final Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new ElementNotFoundException("Customer with id " + customerId + " doesn't exist."));

        final Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new ElementNotFoundException("Book with id " + bookId + " doesn't exist."));

        customer.addBookToWishlist(book);
        return this.customerRepository.save(customer);
    }

    @Override
    public Customer removeBookFromWishlist(final Long customerId, final Long bookId) throws ElementNotFoundException {
        final Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new ElementNotFoundException("Customer with id " + customerId + " doesn't exist."));

        final Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new ElementNotFoundException("Book with id " + bookId + " doesn't exist."));

        customer.removeBookFromWishlist(book);
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
