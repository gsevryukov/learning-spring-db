package ru.sevryukov.learningspringdb.dao;

import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

public interface GenreDao {

    void insert(String name);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}
