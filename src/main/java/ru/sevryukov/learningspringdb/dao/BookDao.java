package ru.sevryukov.learningspringdb.dao;

import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.BookEntity;

import java.util.List;

public interface BookDao {

    void insert(BookEntity bookEntity);

    List<Book> getAll();

    void deleteById(long id);
}
