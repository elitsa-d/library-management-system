package com.bosch.library.library.services;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.entities.dto.CustomerCreateDTO;
import com.bosch.library.library.entities.dto.CustomerDTO;
import com.bosch.library.library.entities.mappers.CustomerCreateMapper;
import com.bosch.library.library.entities.mappers.CustomerMapper;
import com.bosch.library.library.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Spy
    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @Spy
    CustomerCreateMapper customerCreateMapper = Mappers.getMapper(CustomerCreateMapper.class);

    @InjectMocks
    CustomerServiceImpl customerService;

    List<Customer> customerList = new ArrayList<>();

    private static final String DEFAULT_FIRST_NAME = "Boris";
    private static final String DEFAULT_LAST_NAME = "Todorov";
    private static final String DEFAULT_BIOGRAPHY = "Test biography";
    private static final String UPDATED_FIRST_NAME = "Petar";
    private static final String UPDATED_LAST_NAME = "Georgiev";
    private static final String UPDATED_BIOGRAPHY = "Lorem ipsum";

    @BeforeEach
    void setUp() {
        this.customerList.add(new Customer(1L, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_BIOGRAPHY));
        this.customerList.add(new Customer(2L, "Anna", "Stoyanova", "Test"));
    }

    @Test
    void testGetAllCustomersReturnsListOfAllCustomersWithLengthTwo() {
        // Arrange mock repository
        Mockito.when(this.customerRepository.findAll()).thenReturn(this.customerList);

        // Retrieve all customers
        final int result = this.customerService.getAllCustomers().size();

        // Assert that list of returned customers has length of 2
        assertEquals(2, result);
    }

    @Test
    void testGetAllCustomersReturnsValidListOfAllCustomers() {
        // Arrange mock repository
        Mockito.when(this.customerRepository.findAll()).thenReturn(this.customerList);

        // Retrieve all customers
        final List<CustomerDTO> result = this.customerService.getAllCustomers();
        final List<CustomerDTO> expectedResult = this.customerMapper.toDTOList(this.customerList);

        // Assert that the right list of customers is returned
        assertEquals(expectedResult, result);
    }

    @Test
    void testCreateCustomerSavesNewCustomerToRepository() {
        // Arrange
        final CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO(UPDATED_FIRST_NAME, UPDATED_LAST_NAME, UPDATED_BIOGRAPHY);
        final Customer customer = this.customerCreateMapper.toEntity(customerCreateDTO);

        // Create customer
        this.customerService.createCustomer(customerCreateDTO);

        // Verify that it is saved
        verify(this.customerRepository, times(1)).save(customer);
    }

    @Test
    void testUpdateCustomerUpdatesAllCustomerInfo() throws ElementNotFoundException {
        // Arrange mock repository
        final Customer customer = this.customerList.get(0);
        Mockito.when(this.customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Change customer's data
        final CustomerDTO updatedCustomer = new CustomerDTO(1L, UPDATED_FIRST_NAME, UPDATED_LAST_NAME, UPDATED_BIOGRAPHY);
        final CustomerDTO result = this.customerService.updateCustomer(updatedCustomer);

        // Assert that all new data is saved and valid
        verify(this.customerRepository, times(1)).save(customer);
        assertEquals(UPDATED_FIRST_NAME, result.getFirstName());
        assertEquals(UPDATED_LAST_NAME, result.getLastName());
        assertEquals(UPDATED_BIOGRAPHY, result.getBiography());
    }

    @Test
    void testUpdateCustomerUpdatesOnlyFirstName() throws ElementNotFoundException {
        // Arrange mock repository
        final Customer customer = this.customerList.get(0);
        Mockito.when(this.customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Change customer's first name
        final CustomerDTO updatedCustomer = new CustomerDTO(1L, UPDATED_FIRST_NAME, null, null);
        final CustomerDTO result = this.customerService.updateCustomer(updatedCustomer);

        // Assert that only the first name is updated
        assertEquals(UPDATED_FIRST_NAME, result.getFirstName());
        assertEquals(DEFAULT_LAST_NAME, result.getLastName());
        assertEquals(DEFAULT_BIOGRAPHY, result.getBiography());
    }

    @Test
    void testUpdateCustomerUpdatesOnlyLastName() throws ElementNotFoundException {
        // Arrange mock repository
        final Customer customer = this.customerList.get(0);
        Mockito.when(this.customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Change customer's last name
        final CustomerDTO updatedCustomer = new CustomerDTO(1L, null, UPDATED_LAST_NAME, null);
        final CustomerDTO result = this.customerService.updateCustomer(updatedCustomer);

        // Assert that only the last name is updated
        assertEquals(DEFAULT_FIRST_NAME, result.getFirstName());
        assertEquals(UPDATED_LAST_NAME, result.getLastName());
        assertEquals(DEFAULT_BIOGRAPHY, result.getBiography());
    }

    @Test
    void testUpdateCustomerUpdatesOnlyBiography() throws ElementNotFoundException {
        // Arrange mock repository
        final Customer customer = this.customerList.get(0);
        Mockito.when(this.customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Change customer's biography
        final CustomerDTO updatedCustomer = new CustomerDTO(1L, null, null, UPDATED_BIOGRAPHY);
        final CustomerDTO result = this.customerService.updateCustomer(updatedCustomer);

        // Assert that only the biography is updated
        assertEquals(DEFAULT_FIRST_NAME, result.getFirstName());
        assertEquals(DEFAULT_LAST_NAME, result.getLastName());
        assertEquals(UPDATED_BIOGRAPHY, result.getBiography());
    }

    @Test
    void testUpdateCustomerThrowsOnInvalidCustomerId() {
        // Arrange mock repository
        Mockito.when(this.customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that updating nonexistent customer throws exception
        final CustomerDTO updatedCustomer = new CustomerDTO(1L, UPDATED_FIRST_NAME, UPDATED_LAST_NAME, UPDATED_BIOGRAPHY);
        assertThrows(
                ElementNotFoundException.class,
                () -> this.customerService.updateCustomer(updatedCustomer),
                "Updating customer's data should throw ElementNotFoundException when customer optional is empty."
        );
    }

    @Test
    void testDeleteCustomer() throws ElementNotFoundException {
        // Arrange mock repository
        final Customer customer = this.customerList.get(0);
        Mockito.when(this.customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Delete a customer
        final Long deletedCustomerId = this.customerService.deleteCustomer(1L);

        // Verify that it is deleted
        verify(this.customerRepository, times(1)).delete(customer);
        assertEquals(1, deletedCustomerId);
    }

    @Test
    void testDeleteCustomerThrowsOnInvalidCustomerId() {
        // Arrange mock repository
        Mockito.when(this.customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that deleting nonexistent customer throws exception
        assertThrows(
                ElementNotFoundException.class,
                () -> this.customerService.deleteCustomer(1L),
                "Deleting customer should throw ElementNotFoundException when customer optional is empty."
        );
    }
}
