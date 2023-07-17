package com.bosch.library.library.services;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.controllers.errors.exceptions.ValidationException;
import com.bosch.library.library.entities.criteria.LocationCriteria;
import com.bosch.library.library.entities.dto.LocationCreateDTO;
import com.bosch.library.library.entities.dto.LocationDTO;

import java.util.List;

/**
 * Service interface for managing locations.
 */
public interface LocationService {

    /**
     * Retrieves a list of locations.
     *
     * @return a list of location DTOs
     */
    List<LocationDTO> getAllLocations(LocationCriteria locationCriteria);


    /**
     * Creates a new location using the provided information.
     *
     * @param location the information for the location to be created including id of the supplier who owns it
     * @return the created location DTO
     * @throws ValidationException      if this location is already recorded in the supplier's list of locations
     * @throws ElementNotFoundException if the supplier with the provided id doesn't exist
     */
    LocationDTO createLocation(LocationCreateDTO location) throws ValidationException, ElementNotFoundException;
}
