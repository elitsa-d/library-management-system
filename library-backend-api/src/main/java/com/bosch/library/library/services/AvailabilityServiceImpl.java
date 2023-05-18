package com.bosch.library.library.services;

import com.bosch.library.library.entities.Availability;
import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.dto.AvailabilityByBookDTO;
import com.bosch.library.library.entities.dto.AvailabilityByLocationDTO;
import com.bosch.library.library.entities.dto.AvailabilityCreateDTO;
import com.bosch.library.library.entities.dto.AvailabilityDTO;
import com.bosch.library.library.entities.mappers.AvailabilityByBookMapper;
import com.bosch.library.library.entities.mappers.AvailabilityByLocationMapper;
import com.bosch.library.library.entities.mappers.AvailabilityMapper;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.repositories.AvailabilityRepository;
import com.bosch.library.library.repositories.BookRepository;
import com.bosch.library.library.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final LocationRepository locationRepository;
    private final BookRepository bookRepository;
    private final AvailabilityByLocationMapper availabilityByLocationMapper;
    private final AvailabilityByBookMapper availabilityByBookMapper;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityServiceImpl(
            final AvailabilityRepository availabilityRepository,
            final LocationRepository locationRepository,
            final BookRepository bookRepository,
            final AvailabilityByLocationMapper availabilityByLocationMapper,
            final AvailabilityByBookMapper availabilityByBookMapper,
            final AvailabilityMapper availabilityMapper
    ) {
        this.availabilityRepository = availabilityRepository;
        this.locationRepository = locationRepository;
        this.bookRepository = bookRepository;
        this.availabilityByLocationMapper = availabilityByLocationMapper;
        this.availabilityByBookMapper = availabilityByBookMapper;
        this.availabilityMapper = availabilityMapper;
    }

    @Override
    public List<AvailabilityByLocationDTO> getAvailableBooksInLocation(final Long id) {
        return this.availabilityByLocationMapper.toDTOList(this.availabilityRepository.findAllByLocationId(id));
    }

    @Override
    public List<AvailabilityByBookDTO> getLocationsWithAvailableBook(final Long id) {
        return this.availabilityByBookMapper.toDTOList(this.availabilityRepository.findAllByBookId(id));
    }

    @Override
    public AvailabilityDTO addBookToLocation(final AvailabilityCreateDTO newAvailability) throws ElementNotFoundException, ValidationException {
        final Long locationId = newAvailability.getLocationId();
        final Long bookId = newAvailability.getBookId();
        final Integer quantity = newAvailability.getQuantity();

        final Location location = this.locationRepository.findById(locationId)
                .orElseThrow(() -> new ElementNotFoundException("Location with id " + locationId + " doesn't exist."));
        final Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new ElementNotFoundException("Book with id " + bookId + " doesn't exist."));

        if (newAvailability.getQuantity() < 0) {
            throw new ValidationException("The provided quantity value is negative.");
        }

        Availability availability = this.availabilityRepository.findByLocationIdAndBookId(locationId, bookId);

        if (availability == null) {
            availability = new Availability(location, book, quantity);
            this.availabilityRepository.save(availability);
        }

        return this.availabilityMapper.toDTO(availability);
    }

    @Override
    public AvailabilityDTO changeBookQuantity(final AvailabilityCreateDTO updatedAvailability) throws ValidationException, ElementNotFoundException {
        final Long locationId = updatedAvailability.getLocationId();
        final Long bookId = updatedAvailability.getBookId();
        final Integer quantity = updatedAvailability.getQuantity();

        if (quantity < 0) {
            throw new ValidationException("The provided quantity value is negative.");
        }

        final Availability availability = this.availabilityRepository.findByLocationIdAndBookId(locationId, bookId);

        if (availability != null) {
            availability.setQuantity(quantity);
        } else {
            throw new ElementNotFoundException("The specified book is not available in the given location.");
        }

        return this.availabilityMapper.toDTO(this.availabilityRepository.save(availability));
    }

    @Override
    public Long removeBookAvailabilityFromLocation(final Long locationId, final Long bookId) throws ElementNotFoundException {
        final Availability availability = this.availabilityRepository.findByLocationIdAndBookId(locationId, bookId);

        if (availability != null) {
            this.availabilityRepository.delete(availability);
        } else {
            throw new ElementNotFoundException("The specified book is not available in the given location.");
        }

        return bookId;
    }
}
