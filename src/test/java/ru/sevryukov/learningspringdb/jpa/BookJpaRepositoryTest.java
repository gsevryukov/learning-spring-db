package ru.sevryukov.learningspringdb.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.repository.BookRepository;
import ru.sevryukov.learningspringdb.repository.GenreRepository;
import ru.sevryukov.learningspringdb.repository.jpa.BookRepositoryJpa;
import ru.sevryukov.learningspringdb.repository.jpa.GenreRepositoryJpa;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({BookRepositoryJpa.class, GenreRepositoryJpa.class})
class BookJpaRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    EntityManager manager;

    @ParameterizedTest
    @MethodSource("getNewBook")
    @DisplayName("Проверка сохранения книги")
    void saveBookTest(Book newBook) {
        var bookSaved = bookRepository.save(newBook);
        assertThat(bookSaved).usingRecursiveComparison().isEqualTo(newBook);
    }

    @ParameterizedTest
    @MethodSource("getDefaultBook")
    @DisplayName("Проверка поиска книги по идентификатору")
    void findByIdTest(Book defaultBook) {
        var id = 100L;
        var bookOptional = bookRepository.findById(id);
        assertThat(bookOptional).isPresent().get().usingRecursiveComparison().isEqualTo(defaultBook);
    }

    @ParameterizedTest
    @MethodSource("getDefaultBook")
    @DisplayName("Проверка поиска всех книг")
    void findAllTest(Book defaultBook) {
        var all = bookRepository.findAll();
        assertEquals(1, all.size());
        assertThat(all.get(0)).usingRecursiveComparison().isEqualTo(defaultBook);
    }

    @ParameterizedTest
    @MethodSource("getDefaultBook")
    @DisplayName("Проверка изменения названия книги")
    void updateByIdTest(Book defaultBook) {
        defaultBook.setName("Честь");
        var updated = bookRepository.findById(defaultBook.getId());
        assertThat(updated).isPresent().get().isNotEqualTo(defaultBook);
    }

    @ParameterizedTest
    @MethodSource("getDefaultBook")
    @DisplayName("Проверка удаления книги")
    void deleteByIdTest(Book defaultBook) {
        var ent = bookRepository.findById(defaultBook.getId()).get();
        bookRepository.removeBook(ent);
        var all = bookRepository.findAll();
        assertEquals(0, all.size());
    }

    @Test
    void getAllByIdsTest() {
        var all = genreRepository.findAllByIds(List.of(100L, 101L));
        assertEquals(2, all.size());
    }

    private static Stream<Book> getDefaultBook() {
        var authors = List.of(new Author(100, "Антон", "Макаренко"));
        var genres = List.of(
                new Genre(100, "Образовательная книга"),
                new Genre(101, "Художественная книга")
        );
        return Stream.of(new Book(100, "Педагогическая поэма", List.of(), authors, genres));
    }

    private static Stream<Book> getNewBook() {
        var authors = List.of(new Author(1, "Фёдор", "Достоевский"));
        var genres = List.of(new Genre(1, "Классическая литература"));
        var newBook = new Book(0, "Преступление и наказание", List.of(), authors, genres);
        return Stream.of(newBook);
    }

}
