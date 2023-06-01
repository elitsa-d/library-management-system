package com.bosch.library.library.repositories;

import com.bosch.library.library.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing locations.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Finds a location by a given address.
     *
     * @param address the address of the location
     * @return the location with the given address, or null if it is not found
     */
    Location findLocationByAddress(String address);
}
