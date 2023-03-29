package ru.sevryukov.learningspringdb.service;

import org.springframework.data.domain.PageRequest;
import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

public interface GenreService {

    Genre addGenre(String name);

    Genre getById(long id);

    List<Genre> getAll(PageRequest pageRequest);

    List<Genre> getAllByIds(List<Long> ids);

    String deleteById(long id);
}
