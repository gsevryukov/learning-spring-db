package ru.sevryukov.learningspringdb.dao;

import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.BookEntity;

import java.util.List;

public interface BookDao {

    void insert(BookEntity bookEntity);

    Book getById(long id);

    List<Book> getAll();

    void editBook(long id, String name, List<Long> authorIds, List<Long> genreIds);

    void deleteById(long id);
}
