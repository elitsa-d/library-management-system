package com.bosch.library.library.controllers;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.controllers.errors.exceptions.ValidationException;
import com.bosch.library.library.entities.criteria.LocationCriteria;
import com.bosch.library.library.entities.dto.LocationCreateDTO;
import com.bosch.library.library.entities.dto.LocationDTO;
import com.bosch.library.library.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Get all locations", description = "This endpoint gives you a list of all locations")
    @GetMapping("/locations")
    public List<LocationDTO> getAllLocations(@ModelAttribute final LocationCriteria locationCriteria) {
        this.logger.info("Get all locations");
        return this.locationService.getAllLocations(locationCriteria);
    }

    /**
     * Endpoint with POST mapping. Creates a new location using the information from the request body.
     *
     * @param locationCreateDTO the information for the location to be created, provided in a LocationCreateDTO object
     * @return response entity with the created location DTO
     * or error message when the provided data is inaccurate
     */
    @PreAuthorize("hasRole('admin') or hasRole('supplier')")
    @Operation(summary = "Create a new location", description = "Add a location by providing information about its address and id of the supplier who owns it")
    @PostMapping("/locations")
    public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody final LocationCreateDTO locationCreateDTO) throws ValidationException, ElementNotFoundException {
        this.logger.info("Save a new location based on the following information: " + locationCreateDTO.toString());
        final LocationDTO locationDTO = this.locationService.createLocation(locationCreateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
    }
}
