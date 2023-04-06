package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Book;

public interface BookCommentService {

    String addBookComment(Book book, String comment);

    String editBookComment(Book book, long commentId, String text);

    String removeBookComment(Book book, long commentId);

}
