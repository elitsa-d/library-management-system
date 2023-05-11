package com.bosch.library.library.services;

import com.bosch.library.library.entities.BookAvailability;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;

import java.util.List;

public interface BookAvailabilityService {

    List<BookAvailability> getAvailableBooksInLocation(Long id);

    List<BookAvailability> getLocationsWithAvailableBook(Long id);

    BookAvailability addBookToLocation(Long locationId, Long bookId, Integer quantity) throws ElementNotFoundException, ValidationException;

    BookAvailability changeBookQuantity(Long locationId, Long bookId, Integer quantity) throws ElementNotFoundException, ValidationException;

    Long removeBookAvailabilityFromLocation(Long locationId, Long bookId) throws ElementNotFoundException;
}
