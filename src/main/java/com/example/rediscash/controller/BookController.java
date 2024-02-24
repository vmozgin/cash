package com.example.rediscash.controller;

import com.example.rediscash.entity.BookEntity;
import com.example.rediscash.mapper.BookMapper;
import com.example.rediscash.model.BookRequest;
import com.example.rediscash.model.BookResponse;
import com.example.rediscash.model.BooksFilter;
import com.example.rediscash.model.BooksListResponse;
import com.example.rediscash.service.BookService;
import com.example.rediscash.service.CategoryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

	private final BookMapper bookMapper;
	private final BookService bookService;
	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<BookResponse> create(@RequestBody BookRequest request) {
		categoryService.saveIfExist(request.getCategory());
		BookEntity newBook = bookService.save(bookMapper.bookCreateRequestToBookEntity(request));

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(bookMapper.bookEntityToBookResponse(newBook));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookResponse> update(@PathVariable("id") UUID id, @RequestBody BookRequest request) {
		categoryService.saveIfExist(request.getCategory());
		BookEntity updatedBook = bookService.update(request, id);

		return ResponseEntity.ok(bookMapper.bookEntityToBookResponse(updatedBook));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
		bookService.deleteById(id);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/filter")
	public ResponseEntity<BooksListResponse> filterBy(@RequestBody BooksFilter filter) {
		return ResponseEntity.ok(bookMapper.booksEntitiesListToBooksListResponse(bookService.filterBy(filter)));
	}

	@GetMapping("/findBy")
	public ResponseEntity<BookResponse> findBy(@RequestParam String title, @RequestParam String author) {
		return ResponseEntity.ok(bookMapper.bookEntityToBookResponse(bookService.findByTitleAndAuthor(title, author)));
	}
}
