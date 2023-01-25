package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

public interface GenreService {

    void addGenre(String name);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteGenre(long id);
}
