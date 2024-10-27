package com.library.repository.impl;

import com.library.model.Student;
import com.library.repository.StudentRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Student> findById(String id) {
        var query = entityManager.createQuery(
            "SELECT s FROM Student s WHERE s.id = :id", Student.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Student student) {
        if (findById(student.getId()).isPresent()) {
            entityManager.merge(student);
        } else {
            entityManager.persist(student);
        }
    }
}