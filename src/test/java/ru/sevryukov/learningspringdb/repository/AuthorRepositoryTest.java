package ru.sevryukov.learningspringdb.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.sevryukov.learningspringdb.model.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Проверка удаления автора по идентификатору")
    void deleteByIdTest() {
        var newAuthor = authorRepository.save(new Author("Иван", "Тургенев"));
        assertThrows(EmptyResultDataAccessException.class, () -> authorRepository.deleteById(78L));
        authorRepository.deleteById(newAuthor.getId());
        var all = authorRepository.findAll();
        assertEquals(1, all.size());
    }

}
