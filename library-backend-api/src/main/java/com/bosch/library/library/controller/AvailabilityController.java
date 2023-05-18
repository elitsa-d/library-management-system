package com.bosch.library.library.controller;

import com.bosch.library.library.entities.dto.AvailabilityByBookDTO;
import com.bosch.library.library.entities.dto.AvailabilityByLocationDTO;
import com.bosch.library.library.entities.dto.AvailabilityCreateDTO;
import com.bosch.library.library.entities.dto.AvailabilityDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.services.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    public AvailabilityController(final AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/availability/books-in-location/{id}")
    public List<AvailabilityByLocationDTO> getAvailableBooksInLocation(@PathVariable final Long id) {
        return this.availabilityService.getAvailableBooksInLocation(id);
    }

    @GetMapping("/availability/locations-having-book/{id}")
    public List<AvailabilityByBookDTO> getLocationsWithAvailableBook(@PathVariable final Long id) {
        return this.availabilityService.getLocationsWithAvailableBook(id);
    }

    @PostMapping("/availability")
    public ResponseEntity<?> addBookToLocation(@Valid @RequestBody final AvailabilityCreateDTO newAvailability) {
        try {
            final AvailabilityDTO availabilityDTO = this.availabilityService.addBookToLocation(newAvailability);
            return ResponseEntity.status(HttpStatus.OK).body(availabilityDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (final ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/availability")
    public ResponseEntity<?> changeBookQuantity(@Valid @RequestBody final AvailabilityCreateDTO updatedAvailability) {
        try {
            final AvailabilityDTO availabilityDTO = this.availabilityService.changeBookQuantity(updatedAvailability);
            return ResponseEntity.status(HttpStatus.OK).body(availabilityDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (final ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/availability/location/{locationId}/book/{bookId}")
    public ResponseEntity<?> removeBookAvailabilityFromLocation(@PathVariable final Long locationId, @PathVariable final Long bookId) {
        try {
            final Long removedBookId = this.availabilityService.removeBookAvailabilityFromLocation(locationId, bookId);
            return ResponseEntity.status(HttpStatus.OK).body(removedBookId);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
