package ru.sevryukov.learningspringdb.dao;

import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

public interface GenreDao {

    void insert(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    List<Long> getAllIds();

    void deleteById(long id);
}
