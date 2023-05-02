package com.bosch.library.library.services;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.exceptions.InvalidLocationIdException;
import com.bosch.library.library.exceptions.InvalidSupplierIdException;
import com.bosch.library.library.repositories.LocationRepository;
import com.bosch.library.library.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplTest {
    @Mock
    SupplierRepository supplierRepository;

    @Mock
    LocationRepository locationRepository;

    @InjectMocks
    SupplierServiceImpl supplierService;

    final List<Supplier> supplierList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        this.supplierList.add(new Supplier(1L, "Orange", "bookstore", 0));
        this.supplierList.add(new Supplier(2L, "Sofia University", "university library", 0));
    }

    @Test
    void testGetAllSuppliersReturnsListOfAllSuppliersWithLengthTwo() {
        // Arrange mock repository
        Mockito.when(this.supplierRepository.findAll()).thenReturn(this.supplierList);

        // Retrieve all suppliers
        final int result = this.supplierService.getAllSuppliers().size();

        // Assert that list of returned suppliers has length of 2
        assertEquals(2, result);
    }

    @Test
    void testGetAllSuppliersReturnsValidListOfAllSuppliers() {
        // Arrange mock repository
        Mockito.when(this.supplierRepository.findAll()).thenReturn(this.supplierList);

        // Retrieve all suppliers
        final List<Supplier> result = this.supplierService.getAllSuppliers();

        // Assert that the right list of suppliers is returned
        assertEquals(this.supplierList, result);
    }

    @Test
    void testCreateSupplierSavesNewSupplierToRepository() {
        // Arrange
        final Supplier supplier = new Supplier(3L, "Svetlina", "community center", 0);

        // Create supplier
        this.supplierService.createSupplier(supplier);

        // Verify that it is saved
        verify(this.supplierRepository, times(1)).save(supplier);
    }

    @Test
    void testAddNewLocationSavesLocationToSuppliersList() throws InvalidSupplierIdException, InvalidLocationIdException {
        // Arrange mock repository
        final Supplier supplier = this.supplierList.get(0);
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        Mockito.when(this.supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        final Location location = new Location(1L, "opalchenska 10");
        Mockito.when(this.locationRepository.findById(1L)).thenReturn(Optional.of(location));

        // Add new location to existing supplier
        final Supplier savedSupplier = this.supplierService.addNewLocation(1L, 1L);

        // Verify supplier is saved with the new location
        verify(this.supplierRepository, times(1)).save(supplier);
        assertTrue(savedSupplier.getLocations().contains(location));
    }

    @Test
    void testAddNewLocationIncreasesSuppliersListSizeWithOne() throws InvalidSupplierIdException, InvalidLocationIdException {
        // Arrange mock repository
        final Supplier supplier = this.supplierList.get(0);
        supplier.addLocation(new Location(2L, "vitoshka 14"));
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        Mockito.when(this.supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        final Location location = new Location(1L, "opalchenska 10");
        Mockito.when(this.locationRepository.findById(1L)).thenReturn(Optional.of(location));

        // Add new location to existing supplier who already has other locations
        final Supplier savedSupplier = this.supplierService.addNewLocation(1L, 1L);

        // Verify that the locations list size increments by one after adding new location
        final int supplierLocationSize = savedSupplier.getLocations().size();
        assertEquals(2, supplierLocationSize);
    }

    @Test
    void testAddNewLocationThrowsOnInvalidSupplierId() {
        // Arrange mock repository
        final Location location = new Location(1L, "opalchenska 10");
        Mockito.when(this.locationRepository.findById(1L)).thenReturn(Optional.of(location));
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that adding location to nonexistent supplier throws exception
        assertThrows(
                InvalidSupplierIdException.class,
                () -> this.supplierService.addNewLocation(1L, 1L),
                "Adding new location should throw InvalidSupplierIdException when supplier optional is empty."
        );
    }

    @Test
    void testAddNewLocationThrowsOnInvalidLocationId() {
        // Arrange mock repository
        final Supplier supplier = this.supplierList.get(0);
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        Mockito.when(this.locationRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that adding nonexistent location to supplier throws exception
        assertThrows(
                InvalidLocationIdException.class,
                () -> this.supplierService.addNewLocation(1L, 1L),
                "Adding new location should throw InvalidLocationIdException when location optional is empty."
        );
    }
}
