package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.entities.dto.SupplierDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper extends DefaultMapper<SupplierDTO, Supplier> {
}
