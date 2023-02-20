package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

public interface GenreService {

    Genre addGenre(String name);

    Genre getById(long id);

    List<Genre> getAll();

    List<Genre> getAllByIds(List<Long> ids);

    void deleteById(long id);
}
