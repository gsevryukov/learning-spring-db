package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Author;

import java.util.List;

public interface AuthorService {

    void addAuthor(String firstName, String lastName);

    Author getById(long id);

    List<Author> getAll();

    List<Author> getAllByIds(List<Long> ids);

    void deleteAuthor(long id);
}
