package com.bosch.library.library.services;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.entities.dto.CustomerCreateDTO;
import com.bosch.library.library.entities.dto.CustomerDTO;
import com.bosch.library.library.entities.mappers.CustomerCreateMapper;
import com.bosch.library.library.entities.mappers.CustomerMapper;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.repositories.BookRepository;
import com.bosch.library.library.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;
    private final CustomerMapper customerMapper;
    private final CustomerCreateMapper customerCreateMapper;

    public CustomerServiceImpl(
            final CustomerRepository customerRepository,
            final BookRepository bookRepository,
            final CustomerMapper customerMapper,
            final CustomerCreateMapper customerCreateMapper
    ) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.customerMapper = customerMapper;
        this.customerCreateMapper = customerCreateMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return this.customerMapper.toDTOList(this.customerRepository.findAll());
    }

    @Override
    public CustomerDTO createCustomer(final CustomerCreateDTO customerCreateDTO) {
        final Customer customer = this.customerCreateMapper.toEntity(customerCreateDTO);
        return this.customerMapper.toDTO(this.customerRepository.save(customer));
    }

    @Override
    public CustomerDTO updateCustomer(final CustomerDTO updatedCustomer) throws ElementNotFoundException {
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

        return this.customerMapper.toDTO(this.customerRepository.save(customer));
    }

    @Override
    public CustomerDTO addBookToWishlist(final Long customerId, final Long bookId) throws ElementNotFoundException {
        final Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new ElementNotFoundException("Customer with id " + customerId + " doesn't exist."));

        final Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new ElementNotFoundException("Book with id " + bookId + " doesn't exist."));

        if (!customer.getWishlist().contains(book)) {
            customer.addBookToWishlist(book);
        }

        return this.customerMapper.toDTO(this.customerRepository.save(customer));
    }

    @Override
    public CustomerDTO removeBookFromWishlist(final Long customerId, final Long bookId) throws ElementNotFoundException {
        final Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new ElementNotFoundException("Customer with id " + customerId + " doesn't exist."));

        final Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new ElementNotFoundException("Book with id " + bookId + " doesn't exist."));

        customer.removeBookFromWishlist(book);
        return this.customerMapper.toDTO(this.customerRepository.save(customer));
    }

    @Override
    public Long deleteCustomer(final Long id) throws ElementNotFoundException {
        final Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Customer with id " + id + " doesn't exist."));

        this.customerRepository.delete(customer);
        return id;
    }
}
