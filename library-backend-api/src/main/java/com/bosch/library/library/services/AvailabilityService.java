package com.bosch.library.library.services;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.controllers.errors.exceptions.ValidationException;
import com.bosch.library.library.entities.dto.AvailabilityByBookDTO;
import com.bosch.library.library.entities.dto.AvailabilityByLocationDTO;
import com.bosch.library.library.entities.dto.AvailabilityCreateDTO;
import com.bosch.library.library.entities.dto.AvailabilityDTO;

import java.util.List;

/**
 * Service interface for managing books' availability in different locations.
 */
public interface AvailabilityService {

    /**
     * Retrieves a list of books available in the location with the provided id.
     *
     * @param id the id of the location whose available books are retrieved
     * @return a list of availability DTOs with information about the books and their quantity
     */
    List<AvailabilityByLocationDTO> getAvailableBooksInLocation(Long id);

    /**
     * Retrieves a list of locations that have the book with the provided id available in them.
     *
     * @param id the id of the book whose locations are retrieved
     * @return a list of availability DTOs with information about the locations and the quantity in which they have the book
     */
    List<AvailabilityByBookDTO> getLocationsWithAvailableBook(Long id);

    /**
     * Adds a book to a location in a certain quantity based on the provided information.
     *
     * @param newAvailability the information about book id, location id and quantity
     * @return the created availability DTO
     * @throws ElementNotFoundException if the location or the book with the provided id doesn't exist
     * @throws ValidationException      if the provided quantity is negative
     */
    AvailabilityDTO addBookToLocation(AvailabilityCreateDTO newAvailability) throws ElementNotFoundException, ValidationException;

    /**
     * Changes the quantity of a book in a certain location based on the provided information.
     *
     * @param updatedAvailability the information about book id, location id and updated quantity
     * @return the updated availability DTO
     * @throws ElementNotFoundException if the book was not available in the provided location in the first place
     * @throws ValidationException      if the provided quantity is negative
     */
    AvailabilityDTO changeBookQuantity(AvailabilityCreateDTO updatedAvailability) throws ElementNotFoundException, ValidationException;

    /**
     * Makes the given book unavailable in the given location
     *
     * @param locationId the id of the location where the book is to be made unavailable
     * @param bookId     the id of the book to be made unavailable
     * @return the id of the book made unavailable
     * @throws ElementNotFoundException if the book was not available in the provided location in the first place
     */
    Long removeBookAvailabilityFromLocation(Long locationId, Long bookId) throws ElementNotFoundException;
}
