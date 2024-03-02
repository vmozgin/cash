package com.example.rediscash;

import com.example.rediscash.controller.BookController;
import com.example.rediscash.model.BooksFilter;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Sql("classpath:db/init.sql")
@Transactional
@Testcontainers
public class AbstractTest {

	@Autowired
	protected RedisTemplate<String,Object> redisTemplate;
	@Autowired
	protected BookController bookController;

	protected static PostgreSQLContainer postgreSQLContainer;
	@Container
	private static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:7.0.12"))
			.withExposedPorts(6379)
			.withReuse(true);

	static {
		DockerImageName postgres = DockerImageName.parse("postgres:12.3");

		postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(postgres)
				.withReuse(true);
		postgreSQLContainer.start();
	}

	@DynamicPropertySource
	public static void registerProperties(DynamicPropertyRegistry registry) {
		String jdbcUrl = postgreSQLContainer.getJdbcUrl();
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
		registry.add("spring.datasource.url", () -> jdbcUrl);

		registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
		registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
	}

	@BeforeEach
	public void before() {
		redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
	}

	protected void initCash() {
		bookController.findBy("title_1", "author_1");
		bookController.findBy("title_2", "author_2");

		BooksFilter filter = new BooksFilter("category_1");
		bookController.filterBy(filter);
		BooksFilter filter2 = new BooksFilter("category_2");
		bookController.filterBy(filter2);

		Assertions.assertTrue(redisTemplate.hasKey("findByTitleAndAuthor::title_1author_1"));
		Assertions.assertTrue(redisTemplate.hasKey("findByTitleAndAuthor::title_2author_2"));
		Assertions.assertTrue(redisTemplate.hasKey("filterByCategory::category_1"));
		Assertions.assertTrue(redisTemplate.hasKey("filterByCategory::category_2"));
	}
}
