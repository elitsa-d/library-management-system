package com.bosch.library.library.services;

import com.bosch.library.library.entities.dto.AvailabilityByBookDTO;
import com.bosch.library.library.entities.dto.AvailabilityByLocationDTO;
import com.bosch.library.library.entities.dto.AvailabilityCreateDTO;
import com.bosch.library.library.entities.dto.AvailabilityDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;

import java.util.List;

public interface AvailabilityService {

    List<AvailabilityByLocationDTO> getAvailableBooksInLocation(Long id);

    List<AvailabilityByBookDTO> getLocationsWithAvailableBook(Long id);

    AvailabilityDTO addBookToLocation(AvailabilityCreateDTO newAvailability) throws ElementNotFoundException, ValidationException;

    AvailabilityDTO changeBookQuantity(AvailabilityCreateDTO updatedAvailability) throws ElementNotFoundException, ValidationException;

    Long removeBookAvailabilityFromLocation(Long locationId, Long bookId) throws ElementNotFoundException;
}
