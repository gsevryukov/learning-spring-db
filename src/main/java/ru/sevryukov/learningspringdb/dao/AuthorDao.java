package ru.sevryukov.learningspringdb.dao;

import ru.sevryukov.learningspringdb.model.Author;

import java.util.List;

public interface AuthorDao {

    void insert(String firstName, String lastName);

    Author getById(long id);

    List<Author> getAll();

    List<Author> getAllByIds(List<Long> ids);

    void deleteById(long id);
}
