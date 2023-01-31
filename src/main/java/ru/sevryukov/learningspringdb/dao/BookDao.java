package ru.sevryukov.learningspringdb.dao;

import ru.sevryukov.learningspringdb.model.BookEntity;

import java.util.List;

public interface BookDao {

    void insert(String name, List<Long> authorIds, List<Long> genreIds);

    BookEntity getById(long id);

    List<BookEntity> getAll();

    void editBook(long id, String name, List<Long> authorIds, List<Long> genreIds);

    void deleteById(long id);
}
