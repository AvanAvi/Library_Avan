package com.library.service;

import com.library.model.Book;
import com.library.model.Student;
import com.library.repository.BookRepository;
import com.library.repository.StudentRepository;
import com.library.exception.LibraryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private StudentRepository studentRepository;

    private LibraryService libraryService;
    private Student student;
    private Book book;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService(bookRepository, studentRepository);
        student = new Student("1", "John Doe", "Computer Science");
        book = new Book("123", "Clean Code", "Robert Martin");
    }

    @Test
    void shouldBorrowBookSuccessfully() {
        when(studentRepository.findById("1")).thenReturn(Optional.of(student));
        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(book));

        libraryService.borrowBook("1", "123");

        verify(studentRepository).save(student);
        verify(bookRepository).save(book);
        assertFalse(book.isAvailable());
        assertEquals(1, student.getBorrowedBooks().size());
    }

    @Test
    void shouldThrowExceptionWhenBookNotAvailable() {
        book.setAvailable(false);
        when(studentRepository.findById("1")).thenReturn(Optional.of(student));
        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(book));

        assertThrows(LibraryException.class, () -> 
            libraryService.borrowBook("1", "123")
        );
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(LibraryException.class, () -> 
            libraryService.borrowBook("1", "123")
        );
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        when(studentRepository.findById("1")).thenReturn(Optional.of(student));
        when(bookRepository.findByIsbn("123")).thenReturn(Optional.empty());

        assertThrows(LibraryException.class, () -> 
            libraryService.borrowBook("1", "123")
        );
    }

    @Test
    void shouldReturnBookSuccessfully() {
        student.borrowBook(book);
        when(studentRepository.findById("1")).thenReturn(Optional.of(student));
        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(book));

        libraryService.returnBook("1", "123");

        verify(studentRepository).save(student);
        verify(bookRepository).save(book);
        assertTrue(book.isAvailable());
        assertTrue(student.getBorrowedBooks().isEmpty());
    }
}