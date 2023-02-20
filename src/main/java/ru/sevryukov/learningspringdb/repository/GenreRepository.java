package ru.sevryukov.learningspringdb.repository;

import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    List<Genre> findAllByIds(List<Long> ids);

    void removeGenre(Genre genre);
}
