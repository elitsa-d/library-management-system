package com.bosch.library.library.controller;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.services.LocationService;
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
    public List<Location> getAllLocations() {
        return this.locationService.getAllLocations();
    }

    @PostMapping("/locations")
    public Location createLocation(@RequestBody final Location location) {
        return this.locationService.createLocation(location);
    }
}
