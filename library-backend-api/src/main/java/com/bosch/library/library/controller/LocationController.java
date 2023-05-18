package com.bosch.library.library.controller;

import com.bosch.library.library.entities.dto.LocationCreateDTO;
import com.bosch.library.library.entities.dto.LocationDTO;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.services.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class LocationController {
    private final LocationService locationService;

    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public List<LocationDTO> getAllLocations() {
        return this.locationService.getAllLocations();
    }

    @PostMapping("/locations")
    public ResponseEntity<?> createLocation(@Valid @RequestBody final LocationCreateDTO locationCreateDTO) {
        try {
            final LocationDTO locationDTO = this.locationService.createLocation(locationCreateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
        } catch (final ElementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (final ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
