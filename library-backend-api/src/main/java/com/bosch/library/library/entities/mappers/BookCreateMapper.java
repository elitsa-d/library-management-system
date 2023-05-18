package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.dto.BookCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookCreateMapper extends DefaultMapper<BookCreateDTO, Book> {
}
