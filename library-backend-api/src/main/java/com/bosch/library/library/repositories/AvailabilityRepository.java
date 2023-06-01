package com.bosch.library.library.repositories;

import com.bosch.library.library.entities.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing books' availability in different locations.
 */
@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    /**
     * Finds all availabilities by a given location id.
     *
     * @param id the id of the location
     * @return a list of availabilities with the given location id, or null if nothing is found
     */
    List<Availability> findAllByLocationId(Long id);

    /**
     * Finds all availabilities by a given book id.
     *
     * @param id the id of the book
     * @return a list of availabilities with the given book id, or null if nothing is found
     */
    List<Availability> findAllByBookId(Long id);

    /**
     * Finds all availabilities by a given location id and book id.
     *
     * @param locationId the id of the location
     * @param bookId     the id of the book
     * @return a list of availabilities with the given location id and book id, or null if nothing is found
     */
    Availability findByLocationIdAndBookId(Long locationId, Long bookId);
}
