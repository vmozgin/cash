package com.example.rediscash;


import com.example.rediscash.model.BookRequest;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RedisCashTests extends AbstractTest {

	@Test
	public void whenCreateBook_thenClearOnlyCategoryCashFromRequest() {
		initCash();

		var request = new BookRequest("title_3", "author_3", "category_2");
		bookController.create(request);

		Assertions.assertTrue(redisTemplate.hasKey("findByTitleAndAuthor::title_1author_1"));
		Assertions.assertTrue(redisTemplate.hasKey("findByTitleAndAuthor::title_2author_2"));
		Assertions.assertTrue(redisTemplate.hasKey("filterByCategory::category_1"));
		Assertions.assertFalse(redisTemplate.hasKey("filterByCategory::category_2"));
	}

	@Test
	public void whenUpdateBook_thenClearAllTitleAndAuthorCashAndCategoryCashFromRequest() {
		initCash();

		var updateRequest = new BookRequest("updated_title_1", "updated_author_1", "category_1");
		bookController.update(UUID.fromString("282f05a1-89a9-4ff7-93c5-985071867901"), updateRequest);

		Assertions.assertFalse(redisTemplate.hasKey("findByTitleAndAuthor::title_1author_1"));
		Assertions.assertFalse(redisTemplate.hasKey("findByTitleAndAuthor::title_2author_2"));
		Assertions.assertFalse(redisTemplate.hasKey("filterByCategory::category_1"));
		Assertions.assertTrue(redisTemplate.hasKey("filterByCategory::category_2"));
	}

	@Test
	public void whenDeleteBook_thenClearAllCash() {
		initCash();
		bookController.delete(UUID.fromString("282f05a1-89a9-4ff7-93c5-985071867901"));

		Assertions.assertTrue(redisTemplate.keys("*").isEmpty());
	}
}
