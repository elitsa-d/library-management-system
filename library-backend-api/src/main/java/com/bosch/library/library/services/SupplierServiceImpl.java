package com.bosch.library.library.services;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.exceptions.InvalidLocationIdException;
import com.bosch.library.library.exceptions.InvalidSupplierIdException;
import com.bosch.library.library.repositories.LocationRepository;
import com.bosch.library.library.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final LocationRepository locationRepository;

    public SupplierServiceImpl(final SupplierRepository supplierRepository, final LocationRepository locationRepository) {
        this.supplierRepository = supplierRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return this.supplierRepository.findAll();
    }

    @Override
    public Supplier createSupplier(final Supplier supplier) {
        return this.supplierRepository.save(supplier);
    }

    @Override
    public Supplier addNewLocation(final Long supplierId, final Long locationId) throws InvalidSupplierIdException, InvalidLocationIdException {
        final Supplier supplier = this.supplierRepository.findById(supplierId)
                .orElseThrow(() -> new InvalidSupplierIdException("Supplier with id " + supplierId + " doesn't exist."));


        final Location location = this.locationRepository.findById(locationId)
                .orElseThrow(() -> new InvalidLocationIdException("Location with id " + locationId + " doesn't exist."));

        supplier.addLocation(location);
        return this.supplierRepository.save(supplier);
    }
}