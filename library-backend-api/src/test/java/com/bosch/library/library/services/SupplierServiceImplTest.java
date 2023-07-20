package com.bosch.library.library.services;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.controllers.errors.exceptions.ValidationException;
import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.entities.dto.LocationDTO;
import com.bosch.library.library.entities.dto.SupplierCreateDTO;
import com.bosch.library.library.entities.dto.SupplierDTO;
import com.bosch.library.library.entities.mappers.LocationMapper;
import com.bosch.library.library.entities.mappers.SupplierCreateMapper;
import com.bosch.library.library.entities.mappers.SupplierMapper;
import com.bosch.library.library.repositories.LocationRepository;
import com.bosch.library.library.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

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

    @Spy
    SupplierMapper supplierMapper = Mappers.getMapper(SupplierMapper.class);

    @Spy
    SupplierCreateMapper supplierCreateMapper = Mappers.getMapper(SupplierCreateMapper.class);

    @Spy
    LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    @InjectMocks
    SupplierServiceImpl supplierService;

    final List<Supplier> supplierList = new ArrayList<>();

    private static final String DEFAULT_SUPPLIER = "Orange";
    private static final String DEFAULT_TYPE = "bookstore";
    private static final Integer DEFAULT_RENTS = 0;

    @BeforeEach
    void setUp() {
        this.supplierList.add(new Supplier(1L, "Orange", "bookstore", 0));
        this.supplierList.add(new Supplier(2L, "Sofia University", "university library", 0));
    }

    @Test
    void testGetAllSuppliersReturnsListOfAllSuppliersWithLengthTwo() {
        // Arrange mock repository
        Mockito.when(this.supplierRepository.findAll(any(Specification.class))).thenReturn(this.supplierList);

        // Retrieve all suppliers
        final int result = this.supplierService.getAllSuppliers(null).size();

        // Assert that list of returned suppliers has length of 2
        assertEquals(2, result);
    }

    @Test
    void testGetAllSuppliersReturnsValidListOfAllSuppliers() {
        // Arrange mock repository
        Mockito.when(this.supplierRepository.findAll(any(Specification.class))).thenReturn(this.supplierList);

        // Retrieve all suppliers
        final List<SupplierDTO> result = this.supplierService.getAllSuppliers(null);
        final List<SupplierDTO> expectedResult = this.supplierMapper.toDTOList(this.supplierList);

        // Assert that the right list of suppliers is returned
        assertEquals(expectedResult, result);
    }

    @Test
    void testCreateSupplierSavesNewSupplierToRepository() throws ValidationException {
        // Arrange
        final SupplierCreateDTO supplierCreateDTO = new SupplierCreateDTO("Svetlina", "community center", null);
        final Supplier supplier = this.supplierCreateMapper.toEntity(supplierCreateDTO);

        // Create supplier
        this.supplierService.createSupplier(supplierCreateDTO);

        // Verify that it is saved
        verify(this.supplierRepository, times(1)).save(supplier);
    }

    @Test
    void testAddNewLocationSavesLocationToSuppliersList() throws ElementNotFoundException {
        // Arrange mock repository
        final Supplier supplier = this.supplierList.get(0);
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        Mockito.when(this.supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        final Location location = new Location(1L, "opalchenska 10");
        Mockito.when(this.locationRepository.findById(1L)).thenReturn(Optional.of(location));

        // Add new location to existing supplier
        final SupplierDTO savedSupplier = this.supplierService.addNewLocation(1L, 1L);

        // Verify supplier is saved with the new location
        verify(this.supplierRepository, times(1)).save(supplier);

        final LocationDTO locationDTO = this.locationMapper.toDTO(location);
        assertTrue(savedSupplier.getLocations().contains(locationDTO));
    }

    @Test
    void testAddNewLocationIncreasesSuppliersListSizeWithOne() throws ElementNotFoundException {
        // Arrange mock repository
        final Supplier supplier = this.supplierList.get(0);
        supplier.addLocation(new Location(2L, "vitoshka 14"));
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        Mockito.when(this.supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        final Location location = new Location(1L, "opalchenska 10");
        Mockito.when(this.locationRepository.findById(1L)).thenReturn(Optional.of(location));

        // Add new location to existing supplier who already has other locations
        final SupplierDTO savedSupplier = this.supplierService.addNewLocation(1L, 1L);

        // Verify that the locations list size increments by one after adding new location
        final int supplierLocationSize = savedSupplier.getLocations().size();
        assertEquals(2, supplierLocationSize);
    }

    @Test
    void testAddNewLocationThrowsOnInvalidSupplierId() {
        // Arrange mock repository
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that adding location to nonexistent supplier throws exception
        assertThrows(
                ElementNotFoundException.class,
                () -> this.supplierService.addNewLocation(1L, 1L),
                "Adding new location should throw ElementNotFoundException when supplier optional is empty."
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
                ElementNotFoundException.class,
                () -> this.supplierService.addNewLocation(1L, 1L),
                "Adding new location should throw ElementNotFoundException when location optional is empty."
        );
    }

    @Test
    void testUpdateSupplierUpdatesAllSupplierInfo() throws ElementNotFoundException {
        // Arrange mock repository
        final Supplier supplier = this.supplierList.get(0);
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        Mockito.when(this.supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        // Change supplier's data
        final SupplierDTO updatedSupplier = new SupplierDTO(1L, "Svetlina", "community center", null);
        final SupplierDTO result = this.supplierService.updateSupplier(updatedSupplier);

        // Assert that all new data is saved and valid
        verify(this.supplierRepository, times(1)).save(supplier);
        assertEquals(updatedSupplier.getName(), result.getName());
    }

    @Test
    void testUpdateSupplierThrowsOnInvalidSupplierId() {
        // Arrange mock repository
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that updating nonexistent supplier throws exception
        final SupplierDTO updatedSupplier = new SupplierDTO(1L, "Svetlina", "community center", null);
        assertThrows(
                ElementNotFoundException.class,
                () -> this.supplierService.updateSupplier(updatedSupplier),
                "Updating supplier's data should throw ElementNotFoundException when supplier optional is empty."
        );
    }

    @Test
    void testDeleteSupplier() throws ElementNotFoundException {
        // Arrange mock repository
        final Supplier supplier = this.supplierList.get(0);
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        // Delete a supplier
        final Long deletedSupplierId = this.supplierService.deleteSupplier(1L);

        // Verify that it is deleted
        verify(this.supplierRepository, times(1)).delete(supplier);
        assertEquals(1, deletedSupplierId);
    }

    @Test
    void testDeleteSupplierThrowsOnInvalidSupplierId() {
        // Arrange mock repository
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that deleting nonexistent supplier throws exception
        assertThrows(
                ElementNotFoundException.class,
                () -> this.supplierService.deleteSupplier(1L),
                "Deleting supplier should throw ElementNotFoundException when supplier optional is empty."
        );
    }
}
