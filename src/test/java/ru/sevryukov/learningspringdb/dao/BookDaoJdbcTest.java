package ru.sevryukov.learningspringdb.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ru.sevryukov.learningspringdb.dao.jdbc.AuthorDaoJdbc;
import ru.sevryukov.learningspringdb.dao.jdbc.BookDaoJdbc;
import ru.sevryukov.learningspringdb.dao.jdbc.GenreDaoJdbc;
import ru.sevryukov.learningspringdb.dao.mappers.BookMapper;
import ru.sevryukov.learningspringdb.model.BookEntity;
import ru.sevryukov.learningspringdb.service.impl.AuthorServiceImpl;
import ru.sevryukov.learningspringdb.service.impl.GenreServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JdbcTest
@Sql({"/schema.sql", "/test-data.sql"})
class BookDaoJdbcTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private BookDaoJdbc bookDaoJdbc;

    @BeforeEach
    void init() {
        var genreService = new GenreServiceImpl(new GenreDaoJdbc(jdbcTemplate));
        var authorService = new AuthorServiceImpl(new AuthorDaoJdbc(jdbcTemplate));
        var bookMapper = new BookMapper(genreService, authorService);
        bookDaoJdbc = new BookDaoJdbc(jdbcTemplate, bookMapper);
    }

    @Test
    void insertBookTest() {
        var entity = getBookEntity();
        bookDaoJdbc.insert(entity);
        var all = bookDaoJdbc.getAll();
        assertEquals(2, all.size());
        assertEquals(entity.getBookName(), all.get(0).getName());
    }

    @Test
    void getByIdTest() {
        var book = bookDaoJdbc.getById(100);
        assertNotNull(book);
        assertEquals(1, book.getAuthorNames().size());
        assertEquals(2, book.getGenreNames().size());
    }

    @Test
    void getAllTest() {
        var allBooks = bookDaoJdbc.getAll();
        assertNotNull(allBooks);
        assertEquals(1, allBooks.size());
    }

    @Test
    void editBookTest() {
        var newName = "Какое-то странное название";
        bookDaoJdbc.editBook(100, newName, List.of(), List.of());
        var bookEdited = bookDaoJdbc.getById(100);
        assertNotNull(bookEdited);
        assertEquals(newName, bookEdited.getName());
        assertEquals(0, bookEdited.getAuthorNames().size());
        assertEquals(0, bookEdited.getGenreNames().size());
    }

    @Test
    void deleteByIdTest() {
        bookDaoJdbc.deleteById(100);
        var allBooks = bookDaoJdbc.getAll();
        assertEquals(0, allBooks.size());
    }

    private static BookEntity getBookEntity() {
        return new BookEntity("Преступление и наказание", List.of(1L), List.of(1L));
    }

}
