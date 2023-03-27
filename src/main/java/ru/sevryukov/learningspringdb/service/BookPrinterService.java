package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Book;

import java.util.List;

public interface BookPrinterService {

    void printBook(Book book);

    void printAllBooks();

    void printBooks(List<Book> books);

    void printCommentsLine(Book book);
}
