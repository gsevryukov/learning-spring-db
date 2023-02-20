package ru.sevryukov.learningspringdb.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.sevryukov.learningspringdb.config.ServiceTestConfig;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = ServiceTestConfig.class)
class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("Проверка удаления жанра по идентификатору")
    void deleteTest() {
        assertThrows(EntityNotFoundException.class, () -> genreService.deleteById(20L));
        var newGenreId = genreService.addGenre("Детектив").getId();
        var all = genreService.getAll();
        assertEquals(3, all.size());
        genreService.deleteById(newGenreId);
        all = genreService.getAll();
        assertEquals(2, all.size());
    }
}
