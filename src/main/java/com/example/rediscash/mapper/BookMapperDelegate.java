package com.example.rediscash.mapper;

import com.example.rediscash.entity.BookEntity;
import com.example.rediscash.model.BookRequest;
import com.example.rediscash.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BookMapperDelegate implements BookMapper{

	@Autowired
	private CategoryService categoryService;

	@Override
	public BookEntity bookCreateRequestToBookEntity(BookRequest source) {
		BookEntity entity = new BookEntity();
		entity.setAuthor(source.getAuthor());
		entity.setTitle(source.getTitle());
		entity.setCategory(categoryService.getByName(source.getCategory()));

		return entity;
	}
}
