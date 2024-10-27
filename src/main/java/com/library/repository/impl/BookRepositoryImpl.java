package com.library.repository.impl;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        var query = entityManager.createQuery(
            "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
        query.setParameter("isbn", isbn);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Book book) {
        if (findByIsbn(book.getIsbn()).isPresent()) {
            entityManager.merge(book);
        } else {
            entityManager.persist(book);
        }
    }
}