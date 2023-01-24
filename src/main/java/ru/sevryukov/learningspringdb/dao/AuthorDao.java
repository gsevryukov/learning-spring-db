package ru.sevryukov.learningspringdb.dao;

import ru.sevryukov.learningspringdb.model.Author;

import java.util.List;

public interface AuthorDao {

    void insert(Author author);

    List<Author> getAll();

    Author getById(long id);

    void deleteById(long id);
}
