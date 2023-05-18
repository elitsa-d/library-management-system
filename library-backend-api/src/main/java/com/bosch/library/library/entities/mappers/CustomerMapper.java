package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.entities.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends DefaultMapper<CustomerDTO, Customer> {
}
