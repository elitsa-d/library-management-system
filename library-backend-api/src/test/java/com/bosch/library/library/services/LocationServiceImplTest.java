package com.bosch.library.library.services;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {
    @Mock
    private LocationRepository locationRepository;

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
        final List<Location> result = this.locationService.getAllLocations();

        // Assert that the right list of locations is returned
        assertEquals(this.locationList, result);
    }

    @Test
    void testCreateLocationSavesNewLocationToRepository() {
        // Arrange
        final Location location = new Location(3L, "bratia miladinovi 42");

        // Create location
        this.locationService.createLocation(location);

        // Verify that it is saved
        verify(this.locationRepository, times(1)).save(location);
    }
}
