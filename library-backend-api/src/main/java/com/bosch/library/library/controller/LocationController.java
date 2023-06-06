package com.bosch.library.library.controller;

import com.bosch.library.library.entities.dto.LocationCreateDTO;
import com.bosch.library.library.entities.dto.LocationDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing books.
 */
@Tag(description = "Access and modify information about the different locations", name = "Locations")
@RequestMapping("/api")
@RestController
public class LocationController {
    private final LocationService locationService;

    Logger logger = LoggerFactory.getLogger(LocationController.class);

    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * Endpoint with GET mapping. Retrieves a list of locations.
     *
     * @return a list of location DTOs
     */
    @Operation(summary = "Get all locations", description = "This endpoint gives you a list of all locations")
    @GetMapping("/locations")
    public List<LocationDTO> getAllLocations() {
        this.logger.info("Get all locations");
        return this.locationService.getAllLocations();
    }

    /**
     * Endpoint with POST mapping. Creates a new location using the information from the request body.
     *
     * @param locationCreateDTO the information for the location to be created, provided in a LocationCreateDTO object
     * @return response entity with the created location DTO
     * or error message when the provided data is inaccurate
     */
    @Operation(summary = "Create a new location", description = "Add a location by providing information about its address and id of the supplier who owns it")
    @PostMapping("/locations")
    public ResponseEntity<?> createLocation(@Valid @RequestBody final LocationCreateDTO locationCreateDTO) {
        try {
            this.logger.info("Save a new location based on the following information: " + locationCreateDTO.toString());
            final LocationDTO locationDTO = this.locationService.createLocation(locationCreateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
        } catch (final ElementNotFoundException e) {
            this.logger.error("Saving the new location failed and ElementNotFoundException occurred with the following message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (final ValidationException e) {
            this.logger.error("Saving the new location failed and ValidationException occurred with the following message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
