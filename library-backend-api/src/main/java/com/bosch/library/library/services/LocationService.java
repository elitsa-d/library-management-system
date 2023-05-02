package com.bosch.library.library.services;

import com.bosch.library.library.entities.Location;

import java.util.List;

public interface LocationService {
    public List<Location> getAllLocations();

    public Location createLocation(Location location);
}
