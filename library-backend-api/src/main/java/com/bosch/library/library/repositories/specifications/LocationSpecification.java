package com.bosch.library.library.repositories.specifications;

import com.bosch.library.library.entities.Location;
import com.bosch.library.library.entities.Location_;
import com.bosch.library.library.entities.criteria.LocationCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface LocationSpecification extends Specification<Location> {

    static Specification<Location> hasCriteria(final LocationCriteria locationCriteria) {

        final List<Predicate> predicates = new ArrayList<>();

        return (root, query, criteriaBuilder) -> {
            if (locationCriteria.getAddress() != null) {
                predicates.add(criteriaBuilder.like(root.get(Location_.ADDRESS), "%" + locationCriteria.getAddress() + "%"));
            }

//            TODO fix this predicate
//            if (locationCriteria.getSupplier() != null) {
//                predicates.add(criteriaBuilder.like(root.join(Location_.supplier).get(Location_.SUPPLIER), "%" + locationCriteria.getSupplier() + "%"));
//            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
