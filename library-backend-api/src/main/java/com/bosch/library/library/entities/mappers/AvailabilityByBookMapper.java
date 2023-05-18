package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Availability;
import com.bosch.library.library.entities.dto.AvailabilityByBookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvailabilityByBookMapper extends DefaultMapper<AvailabilityByBookDTO, Availability> {
}
