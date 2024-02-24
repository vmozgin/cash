package com.example.rediscash.model;

import java.util.UUID;
import lombok.Data;

@Data
public class CategoryResponse {

	private UUID id;
	private String name;
}
