package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.entities.dto.CustomerCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerCreateMapper extends DefaultMapper<CustomerCreateDTO, Customer> {
}
