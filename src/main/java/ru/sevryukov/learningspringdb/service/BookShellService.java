package ru.sevryukov.learningspringdb.service;

public interface BookShellService {

    void addBook();

    void printBook();

    void listAllBooks();

    void editBook();

    void removeBook();

    void addBookComment();

    void editBookComment();

    void removeBookComment();
}
