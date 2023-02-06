package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Book;

import java.util.List;

public interface BookService {

    Book saveBook(Book book);

    Book getById(long id);

    List<Book> getAll();

    void editBook(long id, String bookName);

    void deleteBook(long id);

}
