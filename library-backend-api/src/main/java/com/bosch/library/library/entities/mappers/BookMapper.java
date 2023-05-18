package com.bosch.library.library.entities.mappers;

import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper extends DefaultMapper<BookDTO, Book> {
}