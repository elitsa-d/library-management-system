package com.bosch.library.library.repositories;

import com.bosch.library.library.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing suppliers.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    /**
     * Finds a supplier by a given name.
     *
     * @param name the name of the supplier
     * @return the supplier with the given name, or null if it is not found
     */
    Supplier findSupplierByName(String name);
}
