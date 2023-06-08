package com.bosch.library.library.services;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.entities.dto.SupplierCreateDTO;
import com.bosch.library.library.entities.dto.SupplierDTO;
import com.bosch.library.library.entities.mappers.SupplierCreateMapper;
import com.bosch.library.library.entities.mappers.SupplierMapper;
import com.bosch.library.library.exceptions.ElementNotFoundException;
import com.bosch.library.library.exceptions.ValidationException;
import com.bosch.library.library.repositories.LocationRepository;
import com.bosch.library.library.repositories.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final LocationRepository locationRepository;
    private final SupplierMapper supplierMapper;
    private final SupplierCreateMapper supplierCreateMapper;

    public SupplierServiceImpl(
            final SupplierRepository supplierRepository,
            final LocationRepository locationRepository,
            final SupplierMapper supplierMapper,
            final SupplierCreateMapper supplierCreateMapper
    ) {
        this.supplierRepository = supplierRepository;
        this.locationRepository = locationRepository;
        this.supplierMapper = supplierMapper;
        this.supplierCreateMapper = supplierCreateMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return this.supplierMapper.toDTOList(this.supplierRepository.findAll());
    }

    @Transactional
    @Override
    public SupplierDTO createSupplier(final SupplierCreateDTO supplierCreateDTO) throws ValidationException {
        final String supplierName = supplierCreateDTO.getName();
        Supplier supplier = this.supplierRepository.findSupplierByName(supplierName);

        if (supplier == null) {
            supplier = this.supplierCreateMapper.toEntity(supplierCreateDTO);

            final List<Location> locations = supplier.getLocations();
            locations.forEach(location -> this.locationRepository.findLocationByAddress(location.getAddress()));
            for (final Location location : locations) {
                final Location savedLocation = this.locationRepository.findLocationByAddress(location.getAddress());
                if (savedLocation != null) {
                    throw new ValidationException("The location with address \"" + savedLocation.getAddress() + "\" is already owned by another supplier.");
                }
            }
            return this.supplierMapper.toDTO(this.supplierRepository.save(supplier));
        } else {
            throw new ValidationException("Supplier with name \"" + supplierName + "\" already exists.");
        }
    }

    @Transactional
    @Override
    public SupplierDTO addNewLocation(final Long supplierId, final Long locationId) throws ElementNotFoundException {
        final Supplier supplier = this.supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ElementNotFoundException("Supplier with id " + supplierId + " doesn't exist."));


        final Location location = this.locationRepository.findById(locationId)
                .orElseThrow(() -> new ElementNotFoundException("Location with id " + locationId + " doesn't exist."));

        supplier.addLocation(location);
        return this.supplierMapper.toDTO(this.supplierRepository.save(supplier));
    }
}