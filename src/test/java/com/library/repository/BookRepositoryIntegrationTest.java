package com.library.repository;

import com.library.config.TestContainersConfig;
import com.library.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryIntegrationTest extends TestContainersConfig {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldSaveAndRetrieveBook() {
        Book book = new Book("1234567890", "Test Book", "Test Author");
        bookRepository.save(book);

        Book found = bookRepository.findByIsbn("1234567890").orElseThrow();
        assertEquals("Test Book", found.getTitle());
        assertEquals("Test Author", found.getAuthor());
    }
}