package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.dto.LocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper extends DefaultMapper<LocationDTO, Location> {
}
