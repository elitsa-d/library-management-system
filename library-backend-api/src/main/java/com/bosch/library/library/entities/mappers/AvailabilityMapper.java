package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Availability;
import com.bosch.library.library.entities.dto.AvailabilityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper extends DefaultMapper<AvailabilityDTO, Availability> {
}
