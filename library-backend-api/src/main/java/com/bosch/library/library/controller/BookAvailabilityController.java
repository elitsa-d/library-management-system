package com.bosch.library.library.controller;

import com.bosch.library.library.entities.BookAvailability;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.services.BookAvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class BookAvailabilityController {
    private final BookAvailabilityService bookAvailabilityService;

    public BookAvailabilityController(final BookAvailabilityService availabilityService) {
        this.bookAvailabilityService = availabilityService;
    }

    @GetMapping("/availability/books-in-location/{id}")
    public List<BookAvailability> getAvailableBooksInLocation(@PathVariable final Long id) {
        return this.bookAvailabilityService.getAvailableBooksInLocation(id);
    }

    @GetMapping("/availability/locations-having-book/{id}")
    public List<BookAvailability> getLocationsWithAvailableBook(@PathVariable final Long id) {
        return this.bookAvailabilityService.getLocationsWithAvailableBook(id);
    }

    @PostMapping("/availability/location/{locationId}/book/{bookId}/quantity/{quantity}")
    public ResponseEntity<?> addBookToLocation(@PathVariable final Long locationId, @PathVariable final Long bookId, @PathVariable final Integer quantity) {
        try {
            final BookAvailability bookAvailability = this.bookAvailabilityService.addBookToLocation(locationId, bookId, quantity);
            return ResponseEntity.status(HttpStatus.OK).body(bookAvailability);
        } catch (final ElementNotFoundException | ValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/availability/location/{locationId}/book/{bookId}/quantity/{quantity}")
    public ResponseEntity<?> changeBookQuantity(@PathVariable final Long locationId, @PathVariable final Long bookId, @PathVariable final Integer quantity) {
        try {
            final BookAvailability bookAvailability = this.bookAvailabilityService.changeBookQuantity(locationId, bookId, quantity);
            return ResponseEntity.status(HttpStatus.OK).body(bookAvailability);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (final ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/availability/location/{locationId}/book/{bookId}")
    public ResponseEntity<?> removeBookAvailabilityFromLocation(@PathVariable final Long locationId, @PathVariable final Long bookId) throws ElementNotFoundException {
        try {
            final Long removedBookId = this.bookAvailabilityService.removeBookAvailabilityFromLocation(locationId, bookId);
            return ResponseEntity.status(HttpStatus.OK).body(removedBookId);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
