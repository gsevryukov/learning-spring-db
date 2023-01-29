package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

public interface GenreService {

    void addGenre(String name);

    Genre getById(long id);

    List<Genre> getAll();

    List<Genre> getAllByIds(List<Long> ids);

    void deleteGenre(long id);
}
