package ru.sevryukov.learningspringdb.service;

import java.util.List;

public interface BookShellService {

    String addBook(String name, List<Long> authorIds, List<Long> genreIds);

    String getBook(long id);

    String getBooks(int page, int size);

    String renameBook(long id, String newName);

    String removeBook(long id);

    String addBookComment(long bookId, String comment);

    String editBookComment(long bookId, long commentId, String text);

    String removeBookComment(long bookId, long commentId);
}
