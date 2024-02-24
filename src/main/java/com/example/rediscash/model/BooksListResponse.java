package com.example.rediscash.model;

import java.util.List;
import lombok.Data;

@Data
public class BooksListResponse {

	private List<BookResponse> books;
}
