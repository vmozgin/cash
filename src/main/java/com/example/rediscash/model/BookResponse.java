package com.example.rediscash.model;

import java.util.UUID;
import lombok.Data;

@Data
public class BookResponse {

	private UUID id;
	private String title;
	private String author;
	private CategoryResponse category;
}
