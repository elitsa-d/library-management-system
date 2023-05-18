package com.bosch.library.library.repositories;

import com.bosch.library.library.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findSupplierByName(String name);
}
