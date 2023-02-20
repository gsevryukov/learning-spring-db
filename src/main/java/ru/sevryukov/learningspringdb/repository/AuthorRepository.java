package ru.sevryukov.learningspringdb.repository;

import ru.sevryukov.learningspringdb.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);

    Optional<Author> findById(long id);

    List<Author> findAll();

    List<Author> findAllByIds(List<Long> ids);

    void removeAuthor(Author author);
}
