package com.bosch.library.library.repositories;

import com.bosch.library.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByTitleAndAuthor(String title, String author);
}
