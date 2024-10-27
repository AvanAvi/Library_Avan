package com.library.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    
    @Test
    void shouldCreateBookWithValidData() {
        Book book = new Book("1234567890", "Clean Code", "Robert Martin");
        
        assertEquals("1234567890", book.getIsbn());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert Martin", book.getAuthor());
        assertTrue(book.isAvailable());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldNotCreateBookWithInvalidIsbn(String isbn) {
        assertThrows(IllegalArgumentException.class, () -> 
            new Book(isbn, "Clean Code", "Robert Martin")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldNotCreateBookWithInvalidTitle(String title) {
        assertThrows(IllegalArgumentException.class, () -> 
            new Book("1234567890", title, "Robert Martin")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldNotCreateBookWithInvalidAuthor(String author) {
        assertThrows(IllegalArgumentException.class, () -> 
            new Book("1234567890", "Clean Code", author)
        );
    }

    @Test
    void shouldToggleAvailability() {
        Book book = new Book("1234567890", "Clean Code", "Robert Martin");
        assertTrue(book.isAvailable());
        
        book.setAvailable(false);
        assertFalse(book.isAvailable());
        
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }

    @Test
    void shouldImplementEqualsAndHashCodeBasedOnIsbn() {
        Book book1 = new Book("1234567890", "Clean Code", "Robert Martin");
        Book book2 = new Book("1234567890", "Different Title", "Different Author");
        Book book3 = new Book("0987654321", "Clean Code", "Robert Martin");

        assertEquals(book1, book2);
        assertNotEquals(book1, book3);
        assertEquals(book1.hashCode(), book2.hashCode());
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }
}