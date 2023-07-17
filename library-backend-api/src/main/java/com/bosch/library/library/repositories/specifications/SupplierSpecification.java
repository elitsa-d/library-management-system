package com.bosch.library.library.repositories.specifications;

import com.bosch.library.library.entities.Supplier;
import com.bosch.library.library.entities.Supplier_;
import com.bosch.library.library.entities.criteria.SupplierCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface SupplierSpecification extends Specification<Supplier> {
    static Specification<Supplier> hasCriteria(final SupplierCriteria supplierCriteria) {
        final List<Predicate> predicates = new ArrayList<>();

        return (root, query, criteriaBuilder) -> {
            if (supplierCriteria.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get(Supplier_.NAME), "%" + supplierCriteria.getName() + "%"));
            }

            if (supplierCriteria.getType() != null) {
                predicates.add(criteriaBuilder.like(root.get(Supplier_.TYPE), "%" + supplierCriteria.getType() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
