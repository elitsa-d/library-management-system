package com.bosch.library.library.repositories.specifications;

import com.bosch.library.library.entities.Availability_;
import com.bosch.library.library.entities.Book;
import com.bosch.library.library.entities.Book_;
import com.bosch.library.library.entities.criteria.BookCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface BookSpecification extends Specification<Book> {
    static Specification<Book> hasCriteria(final BookCriteria bookCriteria) {
        final List<Predicate> predicates = new ArrayList<>();

        return (root, query, criteriaBuilder) -> {
            if (bookCriteria.getTitle() != null) {
                predicates.add(criteriaBuilder.like(root.get(Book_.TITLE), "%" + bookCriteria.getTitle() + "%"));
            }

            if (bookCriteria.getAuthor() != null) {
                predicates.add(criteriaBuilder.like(root.get(Book_.AUTHOR), "%" + bookCriteria.getAuthor() + "%"));
            }

            if (bookCriteria.getCategory() != null) {
                predicates.add(criteriaBuilder.like(root.get(Book_.CATEGORY), "%" + bookCriteria.getCategory() + "%"));
            }

            if (bookCriteria.getQuantity() != null) {
                predicates.add(criteriaBuilder.equal(root.join("availabilities").get(Availability_.QUANTITY), bookCriteria.getQuantity()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
