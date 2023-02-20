package ru.sevryukov.learningspringdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAllByIdIn(List<Long> ids);
}
