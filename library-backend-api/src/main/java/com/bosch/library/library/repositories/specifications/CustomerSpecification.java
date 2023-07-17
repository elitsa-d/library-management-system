package com.bosch.library.library.repositories.specifications;

import com.bosch.library.library.entities.Customer;
import com.bosch.library.library.entities.Customer_;
import com.bosch.library.library.entities.criteria.CustomerCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface CustomerSpecification extends Specification<Customer> {
    static Specification<Customer> hasCriteria(final CustomerCriteria customerCriteria) {

        final List<Predicate> predicates = new ArrayList<>();

        return (root, query, criteriaBuilder) -> {
            if (customerCriteria.getFirstName() != null) {
                predicates.add(criteriaBuilder.like(root.get(Customer_.FIRST_NAME), "%" + customerCriteria.getFirstName() + "%"));
            }

            if (customerCriteria.getLastName() != null) {
                predicates.add(criteriaBuilder.like(root.get(Customer_.LAST_NAME), "%" + customerCriteria.getLastName() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
