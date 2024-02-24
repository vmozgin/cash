package com.example.rediscash.repository;

import com.example.rediscash.entity.BookEntity;
import com.example.rediscash.model.BooksFilter;
import org.springframework.data.jpa.domain.Specification;

public interface BookSpecification {

	static Specification<BookEntity> withFilter(BooksFilter booksFilter) {
		return Specification.where(byCategory(booksFilter.getCategory()));
	}

	static Specification<BookEntity> byCategory(String category) {
		return (root, query, criteriaBuilder) -> {
			if (category == null) {
				return null;
			}
			return criteriaBuilder.equal(root.get("category").get("name"), category);
		};
	}
}
