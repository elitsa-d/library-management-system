package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.entities.dto.SupplierCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierCreateMapper extends DefaultMapper<SupplierCreateDTO, Supplier> {
    LocationCreateMapper locationCreateMapper = Mappers.getMapper(LocationCreateMapper.class);

    @Override
    default Supplier toEntity(final SupplierCreateDTO supplierDTO) {
        if (supplierDTO == null) {
            return null;
        }

        final Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setType(supplierDTO.getType());
        if (supplier.getLocations() != null) {
            final List<Location> locations = locationCreateMapper.toEntitiesList(supplierDTO.getLocations());

            if (locations != null) {
                locations.forEach((location -> location.setSupplier(supplier)));
                supplier.getLocations().addAll(locations);
            }
        }

        return supplier;
    }
}
