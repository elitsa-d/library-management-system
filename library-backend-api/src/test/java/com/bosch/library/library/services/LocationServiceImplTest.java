package com.bosch.library.library.services;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.controllers.errors.exceptions.ValidationException;
import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.entities.dto.LocationCreateDTO;
import com.bosch.library.library.entities.dto.LocationDTO;
import com.bosch.library.library.entities.mappers.LocationCreateMapper;
import com.bosch.library.library.entities.mappers.LocationMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {
    @Mock
    private LocationRepository locationRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Spy
    private LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    @Spy
    private LocationCreateMapper locationCreateMapper = Mappers.getMapper(LocationCreateMapper.class);

    @InjectMocks
    LocationServiceImpl locationService;

    final List<Location> locationList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        this.locationList.add(new Location(1L, "opalchenska 10"));
        this.locationList.add(new Location(2L, "vitoshka 14"));
    }

    @Test
    void testGetAllLocationsReturnsListOfAllLocationsWithLengthTwo() {
        // Arrange mock repository
        Mockito.when(this.locationRepository.findAll()).thenReturn(this.locationList);

        // Retrieve all locations
        final int result = this.locationService.getAllLocations().size();

        // Assert that list of returned locations has length of 2
        assertEquals(2, result);
    }

    @Test
    void testGetAllLocationsReturnsValidListOfAllLocations() {
        // Arrange mock repository
        Mockito.when(this.locationRepository.findAll()).thenReturn(this.locationList);

        // Retrieve all locations
        final List<LocationDTO> result = this.locationService.getAllLocations();
        final List<LocationDTO> expectedResult = this.locationMapper.toDTOList(this.locationList);

        // Assert that the right list of locations is returned
        assertEquals(expectedResult, result);
    }

    @Test
    void testCreateLocationSavesNewLocationToRepository() throws ValidationException, ElementNotFoundException {
        // Arrange
        Mockito.when(this.supplierRepository.findById(1L)).thenReturn(Optional.of(new Supplier()));
        final LocationCreateDTO locationCreateDTO = new LocationCreateDTO("bratia miladinovi 42", 1L);
        final Location location = this.locationCreateMapper.toEntity(locationCreateDTO);

        // Create location
        this.locationService.createLocation(locationCreateDTO);

        // Verify that it is saved
        verify(this.locationRepository, times(1)).save(location);
    }
}
