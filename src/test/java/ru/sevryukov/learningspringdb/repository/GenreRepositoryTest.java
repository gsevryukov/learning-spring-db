package ru.sevryukov.learningspringdb.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

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
        var all = genreRepository.findAllByIdIn(List.of(100L, 101L));
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("Проверка удаления жанра по идентификатору")
    void deleteByIdTest() {
        var newGenre = genreRepository.save(new Genre("Сказка"));
        assertThrows(EmptyResultDataAccessException.class, () -> genreRepository.deleteById(78L));
        genreRepository.deleteById(newGenre.getId());
        var all = genreRepository.findAll();
        assertEquals(2, all.size());
    }

}
