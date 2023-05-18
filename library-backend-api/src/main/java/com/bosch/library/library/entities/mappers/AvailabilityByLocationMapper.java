package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Availability;
import com.bosch.library.library.entities.dto.AvailabilityByLocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvailabilityByLocationMapper extends DefaultMapper<AvailabilityByLocationDTO, Availability> {
}
