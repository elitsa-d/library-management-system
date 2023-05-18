package com.bosch.library.library.repositories;

import com.bosch.library.library.entities.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findAllByLocationId(Long id);

    List<Availability> findAllByBookId(Long id);

    Availability findByLocationIdAndBookId(Long locationId, Long bookId);
}
