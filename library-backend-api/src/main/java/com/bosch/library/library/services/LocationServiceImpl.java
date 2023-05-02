package com.bosch.library.library.services;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(final LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> getAllLocations() {
        return this.locationRepository.findAll();
    }

    @Override
    public Location createLocation(final Location location) {
        return this.locationRepository.save(location);
    }
}
