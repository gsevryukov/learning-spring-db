package ru.sevryukov.learningspringdb.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.sevryukov.learningspringdb.config.TestConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {TestConfig.class})
@Sql(scripts = {"/schema.sql", "/test-data.sql"})
class BookServiceTest {

    private static final String BOOK_NAME = "Педагогическая поэма";
    @Autowired
    BookService bookService;

    @Test
    void addBook() {
        var newName = "Кот в сапогах";
        bookService.addBook(newName, List.of(100L), List.of(101L));
        var b = bookService.getById(1);
        assertEquals(1, b.getId());
        assertEquals(newName, b.getName());
    }

    @Test
    void getByIdTest() {
        var book = bookService.getById(100);
        assertNotNull(book);
        assertEquals(BOOK_NAME, book.getName());
    }

    @Test
    void getAllTest() {
        var all = bookService.getAll();
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals(BOOK_NAME, all.get(0).getName());
    }

    @Test
    void editBook() {
        var newName = "Новое название";
        bookService.editBook(100, newName, List.of(100L), List.of(100L, 101L));
        var edited = bookService.getById(100);
        assertNotNull(edited);
        assertEquals(newName, edited.getName());
        assertEquals(1, edited.getAuthorNames().size());
        assertEquals(2, edited.getGenreNames().size());
    }

    @Test
    void deleteBook() {
        bookService.deleteBook(100);
        var all = bookService.getAll();
        assertEquals(0, all.size());
    }

}
