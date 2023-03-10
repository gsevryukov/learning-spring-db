package ru.sevryukov.learningspringdb.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.repository.GenreRepository;
import ru.sevryukov.learningspringdb.repository.jpa.GenreRepositoryJpa;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreJpaRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

    @Test
    @DisplayName("Проверка сохранения жанра")
    void saveTest() {
        var g1 = new Genre(0, "Детектив");
        var g2 = new Genre(0, "Фантастика");
        genreRepository.save(g1);
        genreRepository.save(g2);
        var all = genreRepository.findAll();
        assertEquals(4, all.size());

    }

    @Test
    @DisplayName("Проверка получения жанров по идентификаторам")
    void getAllByIdsTest() {
        var all = genreRepository.findAllByIds(List.of(100L, 101L));
        assertEquals(2, all.size());
    }

}
