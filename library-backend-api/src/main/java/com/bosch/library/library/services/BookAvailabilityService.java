package com.bosch.library.library.services;

import com.bosch.library.library.entities.BookAvailability;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;

import java.util.List;

public interface BookAvailabilityService {

    public List<BookAvailability> getAvailableBooksInLocation(Long id);

    public List<BookAvailability> getLocationsWithAvailableBook(Long id);

    public BookAvailability addBookToLocation(Long locationId, Long bookId, Integer quantity) throws ElementNotFoundException, ValidationException;

    public BookAvailability changeBookQuantity(Long locationId, Long bookId, Integer quantity) throws ElementNotFoundException, ValidationException;

    public Long removeBookAvailabilityFromLocation(Long locationId, Long bookId) throws ElementNotFoundException;
}
