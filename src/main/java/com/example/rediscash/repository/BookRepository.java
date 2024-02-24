package com.example.rediscash.repository;

import com.example.rediscash.entity.BookEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID>, JpaSpecificationExecutor<BookEntity> {

	BookEntity findBookEntityByTitleAndAuthor(String title, String author);
}
