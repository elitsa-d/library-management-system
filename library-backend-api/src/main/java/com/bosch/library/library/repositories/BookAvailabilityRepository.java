package com.bosch.library.library.repositories;

import com.bosch.library.library.entities.BookAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAvailabilityRepository extends JpaRepository<BookAvailability, Long> {
    List<BookAvailability> findAllByLocationId(Long id);

    List<BookAvailability> findAllByBookId(Long id);

    BookAvailability findByLocationIdAndBookId(Long locationId, Long bookId);
}
