package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.sevryukov.learningspringdb.service.AuthorShellService;
import ru.sevryukov.learningspringdb.service.BookShellService;
import ru.sevryukov.learningspringdb.service.GenreShellService;

@ShellComponent
@RequiredArgsConstructor
public class ShellRunnerService {

    private final AuthorShellService authorShellService;
    private final BookShellService bookShellService;
    private final GenreShellService genreShellService;

    @ShellMethod(key = "addAuthor", value = "Add an author")
    public void addAuthor() {
        authorShellService.addAuthor();
    }

    @ShellMethod(key = "showAuthor", value = "Show an author by id")
    public void showAuthor() {
        authorShellService.printAuthor();
    }

    @ShellMethod(key = "listAuthors", value = "Get a list of all authors")
    public void listAuthors() {
        authorShellService.listAllAuthors();
    }

    @ShellMethod(key = "removeAuthor", value = "Remove an author by id")
    public void removeAuthor() {
        authorShellService.removeAuthor();
    }

    @ShellMethod(key = "addBook", value = "Add a book")
    public void addBook() {
        bookShellService.addBook();
    }

    @ShellMethod(key = "showBook", value = "Show a book")
    public void showBook() {
        bookShellService.printBook();
    }

    @ShellMethod(key = "listBooks", value = "Get a list of all books")
    public void listBooks() {
        bookShellService.listAllBooks();
    }

    @ShellMethod(key = "editBook", value = "Edit a book")
    public void editBook() {
        bookShellService.editBook();
    }

    @ShellMethod(key = "removeBook", value = "Remove a book by id")
    public void removeBook() {
        bookShellService.removeBook();
    }

    @ShellMethod(key = "commentBook", value = "Add book comment")
    public void commentBook() {
        bookShellService.addBookComment();
    }

    @ShellMethod(key = "editBookComment", value = "Edit book comment")
    public void editBookComment() {
        bookShellService.editBookComment();
    }

    @ShellMethod(key = "removeBookComment", value = "Remove book comment")
    public void removeBookComment() {
        bookShellService.removeBookComment();
    }

    @ShellMethod(key = "addGenre", value = "Add a genre")
    public void addGenre() {
        genreShellService.addGenre();
    }

    @ShellMethod(key = "showGenre", value = "Show a genre by id")
    public void showGenre() {
        genreShellService.printGenre();
    }

    @ShellMethod(key = "listGenres", value = "Get a list of all genres")
    public void listGenres() {
        genreShellService.listAllGenres();
    }

    @ShellMethod(key = "removeGenre", value = "Remove a genre by id")
    public void removeGenre() {
        genreShellService.removeGenre();
    }

}
