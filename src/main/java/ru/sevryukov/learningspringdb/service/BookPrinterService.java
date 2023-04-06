package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Book;

import java.util.List;

public interface BookPrinterService {

    String getBookString(Book book);

    String getAllBooks(List<Book> books);

}
