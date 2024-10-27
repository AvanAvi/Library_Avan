package com.library.repository;

import com.library.model.Student;
import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findById(String id);
    void save(Student student);
}