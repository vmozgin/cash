package com.example.rediscash.service;

import com.example.rediscash.entity.BookEntity;
import com.example.rediscash.mapper.BookMapper;
import com.example.rediscash.model.BookRequest;
import com.example.rediscash.model.BooksFilter;
import com.example.rediscash.repository.BookRepository;
import com.example.rediscash.repository.BookSpecification;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final BookMapper bookMapper;

	@CacheEvict(value = "filterByCategory", key = "#book.category.name")
	public BookEntity save(BookEntity book) {
		return bookRepository.save(book);
	}

	@Caching(evict = {
			@CacheEvict(value = "findByTitleAndAuthor", allEntries = true),
			@CacheEvict(value = "filterByCategory", key = "#request.category")
	})
	public BookEntity update(BookRequest request, UUID id) {
		BookEntity newEntity = bookMapper.bookCreateRequestToBookEntity(request);
		BookEntity existedEntity = bookRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Книга с ID = %s не найдена", id)));
		newEntity.setId(existedEntity.getId());

		return save(newEntity);
	}

	@Caching(evict = {
			@CacheEvict(value = "findByTitleAndAuthor", allEntries = true),
			@CacheEvict(value = "filterByCategory", allEntries = true)
	})
	public void deleteById(UUID id) {
		bookRepository.deleteById(id);
	}

	@Cacheable(cacheNames = "filterByCategory", key = "#filter.category")
	public List<BookEntity> filterBy(BooksFilter filter) {
		return bookRepository.findAll(BookSpecification.withFilter(filter));
	}

	@Cacheable(value = "findByTitleAndAuthor", key = "#title + #author")
	public BookEntity findByTitleAndAuthor(String title, String author) {
		return bookRepository.findBookEntityByTitleAndAuthor(title, author);
	}
}
