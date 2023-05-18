package com.bosch.library.library.repositories;

import com.bosch.library.library.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findLocationByAddress(String address);
}
