package com.library.service;

import com.library.model.Book;
import com.library.model.Student;
import com.library.repository.BookRepository;
import com.library.repository.StudentRepository;
import com.library.exception.LibraryException;

public class LibraryService {
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public LibraryService(BookRepository bookRepository, StudentRepository studentRepository) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    public void borrowBook(String studentId, String isbn) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new LibraryException("Student not found"));
        Book book = bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new LibraryException("Book not found"));

        if (!book.isAvailable()) {
            throw new LibraryException("Book is not available");
        }

        if (student.getBorrowedBooks().size() >= 3) {
            throw new LibraryException("Student has reached maximum number of books");
        }

        student.borrowBook(book);
        studentRepository.save(student);
        bookRepository.save(book);
    }

    public void returnBook(String studentId, String isbn) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new LibraryException("Student not found"));
        Book book = bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new LibraryException("Book not found"));

        if (!student.getBorrowedBooks().contains(book)) {
            throw new LibraryException("Student has not borrowed this book");
        }

        student.returnBook(book);
        studentRepository.save(student);
        bookRepository.save(book);
    }
}