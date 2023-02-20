package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

public interface EntityService {

    void addBook(String bookName, List<Long> enteredAuthorIds, List<Long> enteredGenreIds);

    Book getBookById(String enteredId);

    List<Book> getAllBooks();

    void renameBook(Long bookId, String newName);

    void deleteBook(Long bookId);

    void addBookComment(long bookId, String text);

    void editCommentById(long commentId, String text);

    void deleteCommentById(Book book, long commentId);

    void addAuthor(String firstName, String lastName);

    Author getAuthorById(String enteredId);

    List<Author> getAllAuthors();

    void removeAuthor(String enteredId);

    void addGenre(String name);

    Genre getGenreById(String enteredId);

    List<Genre> getAllGenres();

    void removeGenre(String enteredId);

}
