package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Book;

import java.util.List;

public interface BookService {

    void addBook(String bookName, List<Long> authorIds, List<Long> genreIds);

    void deleteBook(long id);

    Book getById(long id);

    List<Book> getAll();
}
