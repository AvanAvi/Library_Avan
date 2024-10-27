package com.library.repository;

import com.library.model.Book;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findByIsbn(String isbn);
    void save(Book book);
}