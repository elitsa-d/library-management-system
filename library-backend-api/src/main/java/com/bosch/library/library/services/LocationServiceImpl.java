package com.bosch.library.library.services;

import com.bosch.library.library.controllers.errors.exceptions.ElementNotFoundException;
import com.bosch.library.library.controllers.errors.exceptions.ValidationException;
import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.criteria.LocationCriteria;
import com.bosch.library.library.entities.dto.LocationCreateDTO;
import com.bosch.library.library.entities.dto.LocationDTO;
import com.bosch.library.library.entities.mappers.LocationCreateMapper;
import com.bosch.library.library.entities.mappers.LocationMapper;
import com.bosch.library.library.repositories.LocationRepository;
import com.bosch.library.library.repositories.SupplierRepository;
import com.bosch.library.library.repositories.specifications.LocationSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    private final SupplierRepository supplierRepository;

    private final LocationMapper locationMapper;

    private final LocationCreateMapper locationCreateMapper;

    Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

    public LocationServiceImpl(
            final LocationRepository locationRepository,
            final SupplierRepository supplierRepository,
            final LocationMapper locationMapper,
            final LocationCreateMapper locationCreateMapper
    ) {
        this.locationRepository = locationRepository;
        this.supplierRepository = supplierRepository;
        this.locationMapper = locationMapper;
        this.locationCreateMapper = locationCreateMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<LocationDTO> getAllLocations(final LocationCriteria locationCriteria) {
        return this.locationMapper.toDTOList(this.locationRepository.findAll(LocationSpecification.hasCriteria(locationCriteria)));
    }

    @Transactional
    @Override
    public LocationDTO createLocation(final LocationCreateDTO locationCreateDTO) throws ValidationException, ElementNotFoundException {
        final long startTime = System.nanoTime();
        final Long supplierId = locationCreateDTO.getSupplierId();
        this.supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ElementNotFoundException("Supplier with id " + supplierId + " doesn't exist."));

        Location location = this.locationRepository.findLocationByAddress(locationCreateDTO.getAddress());

        if (location == null) {
            location = this.locationCreateMapper.toEntity(locationCreateDTO);

            final long stopTime = System.nanoTime();
            final long elapsedTime = stopTime - startTime;
            this.logger.debug("It took " + elapsedTime + " milliseconds for creating a new location");
            return this.locationMapper.toDTO(this.locationRepository.save(location));
        } else {
            throw new ValidationException("Supplier with id " + supplierId + " already has this location.");
        }
    }

    @Transactional
    @Override
    public LocationDTO updateLocation(final LocationDTO updatedLocation) throws ElementNotFoundException {
        final Long id = updatedLocation.getId();

        final Location location = this.locationRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Location with id " + id + " doesn't exist."));

        if (updatedLocation.getAddress() != null) {
            location.setAddress(updatedLocation.getAddress());
        }

        return this.locationMapper.toDTO(this.locationRepository.save(location));
    }

    @Transactional
    @Override
    public Long deleteLocation(final Long id) throws ElementNotFoundException {
        final Location location = this.locationRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Location with id " + id + " doesn't exist."));
        this.locationRepository.delete(location);
        return id;
    }
}
