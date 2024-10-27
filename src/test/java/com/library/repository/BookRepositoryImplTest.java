package com.library.repository;

import com.library.model.Book;
import com.library.repository.impl.BookRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BookRepositoryImpl.class)
class BookRepositoryImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldSaveAndFindBook() {
        Book book = new Book("123", "Test Book", "Test Author");
        bookRepository.save(book);

        var found = bookRepository.findByIsbn("123");
        assertTrue(found.isPresent());
        assertEquals("Test Book", found.get().getTitle());
    }

    @Test
    void shouldReturnEmptyWhenBookNotFound() {
        var found = bookRepository.findByIsbn("nonexistent");
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldUpdateExistingBook() {
        Book book = new Book("123", "Test Book", "Test Author");
        bookRepository.save(book);
        
        book.setAvailable(false);
        bookRepository.save(book);

        var found = bookRepository.findByIsbn("123");
        assertTrue(found.isPresent());
        assertFalse(found.get().isAvailable());
    }
}