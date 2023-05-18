package com.bosch.library.library.services;

import com.bosch.library.library.entities.dto.LocationCreateDTO;
import com.bosch.library.library.entities.dto.LocationDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;

import java.util.List;

public interface LocationService {
    List<LocationDTO> getAllLocations();

    LocationDTO createLocation(LocationCreateDTO location) throws ValidationException, ElementNotFoundException;
}
