package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.dto.LocationCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationCreateMapper extends DefaultMapper<LocationCreateDTO, Location> {
    @Override
    @Mapping(source = "supplierId", target = "supplier.id")
    Location toEntity(LocationCreateDTO locationCreateDTO);
}
