package ru.sevryukov.learningspringdb.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.model.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Проверка удаления жанра по идентификаторам")
    void deleteByIdTest() {
        var newGenre = commentRepository.save(new Comment("Умный комментарий"));
        assertThrows(EmptyResultDataAccessException.class, () -> commentRepository.deleteById(78L));
        commentRepository.deleteById(newGenre.getId());
        var all = commentRepository.findAll();
        assertEquals(0, all.size());
    }

}
