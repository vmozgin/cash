package com.example.rediscash.service;

import com.example.rediscash.entity.CategoryEntity;
import com.example.rediscash.repository.CategoryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryEntity saveIfExist(String categoryName) {
		CategoryEntity entity = categoryRepository.findByName(categoryName);
		if (entity != null) {
			return entity;
		} else
			entity = new CategoryEntity();
			entity.setName(categoryName);
			return categoryRepository.save(entity);
	}

	public CategoryEntity getByName(String categoryName) {
		return categoryRepository.findByName(categoryName);
	}
}
