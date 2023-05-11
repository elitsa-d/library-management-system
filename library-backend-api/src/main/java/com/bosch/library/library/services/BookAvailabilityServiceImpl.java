package com.bosch.library.library.services;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.BookAvailability;
import com.bosch.library.library.entities.Location;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.repositories.BookAvailabilityRepository;
import com.bosch.library.library.repositories.BookRepository;
import com.bosch.library.library.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAvailabilityServiceImpl implements BookAvailabilityService {
    private final BookAvailabilityRepository bookAvailabilityRepository;

    private final LocationRepository locationRepository;
    private final BookRepository bookRepository;

    public BookAvailabilityServiceImpl(final BookAvailabilityRepository bookAvailabilityRepository, final LocationRepository locationRepository, final BookRepository bookRepository) {
        this.bookAvailabilityRepository = bookAvailabilityRepository;
        this.locationRepository = locationRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookAvailability> getAvailableBooksInLocation(final Long id) {
        return this.bookAvailabilityRepository.findAllByLocationId(id);
    }

    @Override
    public List<BookAvailability> getLocationsWithAvailableBook(final Long id) {
        return this.bookAvailabilityRepository.findAllByBookId(id);
    }

    @Override
    public BookAvailability addBookToLocation(final Long locationId, final Long bookId, final Integer quantity) throws ElementNotFoundException, ValidationException {
        final Location location = this.locationRepository.findById(locationId)
                .orElseThrow(() -> new ElementNotFoundException("Location with id " + locationId + " doesn't exist."));
        final Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new ElementNotFoundException("Book with id " + bookId + " doesn't exist."));

        if (quantity < 0) {
            throw new ValidationException("The provided quantity value is negative.");
        }

        BookAvailability bookAvailability = this.bookAvailabilityRepository.findByLocationIdAndBookId(locationId, bookId);

        if (bookAvailability == null) {
            bookAvailability = new BookAvailability(location, book, quantity);
            this.bookAvailabilityRepository.save(bookAvailability);
        }

        return bookAvailability;
    }

    @Override
    public BookAvailability changeBookQuantity(final Long locationId, final Long bookId, final Integer quantity) throws ValidationException, ElementNotFoundException {
        if (quantity < 0) {
            throw new ValidationException("The provided quantity value is negative.");
        }

        final BookAvailability bookAvailability = this.bookAvailabilityRepository.findByLocationIdAndBookId(locationId, bookId);

        if (bookAvailability != null) {
            bookAvailability.setQuantity(quantity);
        } else {
            throw new ElementNotFoundException("The specified book is not available in the given location.");
        }

        return this.bookAvailabilityRepository.save(bookAvailability);
    }

    @Override
    public Long removeBookAvailabilityFromLocation(final Long locationId, final Long bookId) throws ElementNotFoundException {
        final BookAvailability bookAvailability = this.bookAvailabilityRepository.findByLocationIdAndBookId(locationId, bookId);

        if (bookAvailability != null) {
            this.bookAvailabilityRepository.delete(bookAvailability);
        } else {
            throw new ElementNotFoundException("The specified book is not available in the given location.");
        }
        
        return bookId;
    }
}
