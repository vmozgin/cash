package com.example.rediscash.mapper;

import com.example.rediscash.entity.BookEntity;
import com.example.rediscash.model.BookRequest;
import com.example.rediscash.model.BookResponse;
import com.example.rediscash.model.BooksListResponse;
import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(BookMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

	@Mapping(target = "category", ignore = true)
	BookEntity bookCreateRequestToBookEntity(BookRequest source);

	BookResponse bookEntityToBookResponse(BookEntity source);

	default BooksListResponse booksEntitiesListToBooksListResponse(List<BookEntity> bookEntityList) {
		BooksListResponse booksListResponse = new BooksListResponse();
		booksListResponse.setBooks(booksEntitiesToBooksList(bookEntityList));

		return booksListResponse;
	}

	List<BookResponse> booksEntitiesToBooksList(List<BookEntity> source);
}
