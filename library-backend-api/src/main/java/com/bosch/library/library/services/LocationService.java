package com.bosch.library.library.services;

import com.bosch.library.library.entities.Location;

import java.util.List;

public interface LocationService {
    List<Location> getAllLocations();

    Location createLocation(Location location);
}
