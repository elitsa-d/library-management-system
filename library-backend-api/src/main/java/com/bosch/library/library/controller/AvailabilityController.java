package com.bosch.library.library.controller;

import com.bosch.library.library.entities.dto.AvailabilityByBookDTO;
import com.bosch.library.library.entities.dto.AvailabilityByLocationDTO;
import com.bosch.library.library.entities.dto.AvailabilityCreateDTO;
import com.bosch.library.library.entities.dto.AvailabilityDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.services.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing books' availability in different locations.
 */
@Tag(description = "Access and modify information about the availability of books in the different locations", name = "Book availability in different locations")
@RequestMapping("/api")
@RestController
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    public AvailabilityController(final AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    /**
     * Endpoint with GET mapping. Retrieves a list of books available in the location with the provided id.
     *
     * @param id the id of the location whose available books are retrieved
     * @return a list of availability DTOs with information about the available books and their quantity
     */
    @Operation(summary = "Get all available books in a given location", description = "Provide a location id to get a list of all books available in it and their current quantity")
    @GetMapping("/availability/books-in-location/{id}")
    public List<AvailabilityByLocationDTO> getAvailableBooksInLocation(@PathVariable final Long id) {
        return this.availabilityService.getAvailableBooksInLocation(id);
    }

    /**
     * Endpoint with GET mapping. Retrieves a list of locations that have the book with the provided id available in them.
     *
     * @param id the id of the book whose locations are retrieved
     * @return a list of availability DTOs with information about the locations and the quantity in which they have the book
     */
    @Operation(summary = "Get all locations that have a given book", description = "Provide a book id to get a list of the locations in which it is available and in what quantity")
    @GetMapping("/availability/locations-having-book/{id}")
    public List<AvailabilityByBookDTO> getLocationsWithAvailableBook(@PathVariable final Long id) {
        return this.availabilityService.getLocationsWithAvailableBook(id);
    }

    /**
     * Endpoint with POST mapping. Adds a book to a location in a certain quantity based on the information from the request body.
     *
     * @param newAvailability the information about book id, location id and quantity, provided in an AvailabilityCreateDTO object
     * @return response entity with the created availability DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Make a book available in a certain location", description = "Provide book id, location id and quantity to make the given book available in the specified location")
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

    /**
     * Endpoint with PUT mapping. Changes the quantity of a book in a certain location based on the information from the request body.
     *
     * @param updatedAvailability the information about book id, location id and updated quantity, provided in an AvailabilityCreateDTO object
     * @return response entity with the updated availability DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Change the quantity of an available book in a given location", description = "Provide book id, location id and the new quantity to change number of available books in the specified location")
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

    /**
     * Endpoint with DELETE mapping. Makes the given book unavailable in the given location
     *
     * @param locationId the id of the location where the book is to be made unavailable
     * @param bookId     the id of the book to be made unavailable
     * @return response entity with the id of the book made unavailable
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Make a book unavailable in a certain location", description = "Provide location id and book id to make the given book unavailable in the specified location")
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
