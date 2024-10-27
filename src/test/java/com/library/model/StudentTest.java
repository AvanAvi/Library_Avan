package com.library.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    
    @Test
    void shouldCreateStudentWithValidData() {
        Student student = new Student("12345", "John Doe", "Computer Science");
        
        assertEquals("12345", student.getId());
        assertEquals("John Doe", student.getName());
        assertEquals("Computer Science", student.getDepartment());
        assertTrue(student.getBorrowedBooks().isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldNotCreateStudentWithInvalidId(String id) {
        assertThrows(IllegalArgumentException.class, () -> 
            new Student(id, "John Doe", "Computer Science")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldNotCreateStudentWithInvalidName(String name) {
        assertThrows(IllegalArgumentException.class, () -> 
            new Student("12345", name, "Computer Science")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldNotCreateStudentWithInvalidDepartment(String department) {
        assertThrows(IllegalArgumentException.class, () -> 
            new Student("12345", "John Doe", department)
        );
    }

    @Test
    void shouldBorrowAndReturnBooks() {
        Student student = new Student("12345", "John Doe", "Computer Science");
        Book book = new Book("1234567890", "Clean Code", "Robert Martin");
        
        student.borrowBook(book);
        assertEquals(1, student.getBorrowedBooks().size());
        assertFalse(book.isAvailable());
        
        student.returnBook(book);
        assertTrue(student.getBorrowedBooks().isEmpty());
        assertTrue(book.isAvailable());
    }

    @Test
    void shouldNotBorrowNullBook() {
        Student student = new Student("12345", "John Doe", "Computer Science");
        assertThrows(IllegalArgumentException.class, () -> 
            student.borrowBook(null)
        );
    }

    @Test
    void shouldNotReturnNullBook() {
        Student student = new Student("12345", "John Doe", "Computer Science");
        assertThrows(IllegalArgumentException.class, () -> 
            student.returnBook(null)
        );
    }

    @Test
    void shouldImplementEqualsAndHashCodeBasedOnId() {
        Student student1 = new Student("12345", "John Doe", "Computer Science");
        Student student2 = new Student("12345", "Different Name", "Different Department");
        Student student3 = new Student("54321", "John Doe", "Computer Science");

        assertEquals(student1, student2);
        assertNotEquals(student1, student3);
        assertEquals(student1.hashCode(), student2.hashCode());
        assertNotEquals(student1.hashCode(), student3.hashCode());
    }

    @Test
    void shouldReturnDefensiveCopyOfBorrowedBooks() {
        Student student = new Student("12345", "John Doe", "Computer Science");
        Book book = new Book("1234567890", "Clean Code", "Robert Martin");
        
        student.borrowBook(book);
        List<Book> borrowedBooks = student.getBorrowedBooks();
        borrowedBooks.clear(); // This should not affect the internal list
        
        assertEquals(1, student.getBorrowedBooks().size());
    }
}