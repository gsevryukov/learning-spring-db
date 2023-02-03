package ru.sevryukov.learningspringdb.dao;

import ru.sevryukov.learningspringdb.model.Book;

import java.util.List;

public interface BookDao {

    void insert(String name, List<Long> authorIds, List<Long> genreIds);

    Book getById(long id);

    List<Book> getAll();

    void editBook(long id, String name, List<Long> authorIds, List<Long> genreIds);

    void deleteById(long id);
}
