package com.library.repository;

import com.library.model.Student;
import com.library.repository.impl.StudentRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(StudentRepositoryImpl.class)
class StudentRepositoryImplTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldSaveAndFindStudent() {
        Student student = new Student("1", "Test Student", "Test Department");
        studentRepository.save(student);

        var found = studentRepository.findById("1");
        assertTrue(found.isPresent());
        assertEquals("Test Student", found.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenStudentNotFound() {
        var found = studentRepository.findById("nonexistent");
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldUpdateExistingStudent() {
        Student student = new Student("1", "Test Student", "Test Department");
        studentRepository.save(student);
        
        Book book = new Book("123", "Test Book", "Test Author");
        student.borrowBook(book);
        studentRepository.save(student);

        var found = studentRepository.findById("1");
        assertTrue(found.isPresent());
        assertEquals(1, found.get().getBorrowedBooks().size());
    }
}