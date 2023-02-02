package ru.sevryukov.learningspringdb.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.sevryukov.learningspringdb.config.TestConfig;
import ru.sevryukov.learningspringdb.dao.jdbc.BookDaoJdbc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql({"/schema.sql", "/test-data.sql"})
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void insertBookTest() {
        var newName = "Преступление и наказание";
        bookDaoJdbc.insert(newName, List.of(100L), List.of(100L));
        var all = bookDaoJdbc.getAll();
        assertEquals(2, all.size());
        assertEquals(newName, all.get(0).getName());
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
        bookDaoJdbc.editBook(100, newName, List.of(100L), List.of(100L, 101L));
        var bookEdited = bookDaoJdbc.getById(100);
        assertNotNull(bookEdited);
        assertEquals(newName, bookEdited.getName());
        assertEquals(1, bookEdited.getAuthorNames().size());
        assertEquals(2, bookEdited.getGenreNames().size());
    }

    @Test
    void deleteByIdTest() {
        bookDaoJdbc.deleteById(100);
        var allBooks = bookDaoJdbc.getAll();
        assertEquals(0, allBooks.size());
    }

}
