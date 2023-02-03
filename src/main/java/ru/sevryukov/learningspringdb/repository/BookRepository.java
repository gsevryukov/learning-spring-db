package ru.sevryukov.learningspringdb.repository;

import ru.sevryukov.learningspringdb.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void updateById(long id, String name);

    void deleteById(long id);
}
