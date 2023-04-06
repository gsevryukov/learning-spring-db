package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.sevryukov.learningspringdb.service.AuthorShellService;
import ru.sevryukov.learningspringdb.service.BookShellService;
import ru.sevryukov.learningspringdb.service.GenreShellService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellRunnerService {

    private final AuthorShellService authorShellService;
    private final BookShellService bookShellService;
    private final GenreShellService genreShellService;

    private static final String NAME_LIMIT = "Size is limited between 2 and 15 chars!";

    @ShellMethod(key = "addAuthor", value = "Add an author")
    public String addAuthor(
            @ShellOption(value = {"-f", "--firstName"})
            @Size(min = 2, max = 15, message = NAME_LIMIT) String firstName,
            @ShellOption(value = {"-l", "--lastName"})
            @Size(min = 2, max = 15, message = NAME_LIMIT) String lastName
    ) {
        return authorShellService.addAuthor(firstName, lastName);
    }

    @ShellMethod(key = "showAuthor", value = "Show an author by id")
    public String showAuthor(@Min(1) long id) {
        return authorShellService.getAuthor(id);
    }

    @ShellMethod(key = "listAuthors", value = "Get a list of all authors")
    public String listAuthors(
            @ShellOption(value = {"-p", "--page"})
            @Min(0) int page,
            @ShellOption(value = {"-s", "--size"})
            @Min(1) int size
    ) {
        return authorShellService.getAllAuthors(page, size);
    }

    @ShellMethod(key = "removeAuthor", value = "Remove an author by id")
    public String removeAuthor(@Min(1) long id) {
        return authorShellService.removeAuthor(id);
    }

    @ShellMethod(key = "addBook", value = "Add a book")
    public String addBook(
            @ShellOption(value = {"-n", "--name"})
            @Size(min = 2, max = 15, message = NAME_LIMIT) String name,
            @ShellOption(value = {"-a", "--authors"}) List<Long> authorIds,
            @ShellOption(value = {"-g", "--genres"}) List<Long> genreIds
    ) {
        return bookShellService.addBook(name, authorIds, genreIds);
    }

    @ShellMethod(key = "showBook", value = "Show a book")
    public String showBook(@Min(1) long id) {
        return bookShellService.getBook(id);
    }

    @ShellMethod(key = "listBooks", value = "Get a list of all books")
    public String listBooks(
            @ShellOption(value = {"-p", "--page"})
            @Min(0) int page,
            @ShellOption(value = {"-s", "--size"})
            @Min(1) int size
    ) {
        return bookShellService.getBooks(page, size);
    }

    @ShellMethod(key = "renameBook", value = "Edit a book")
    public String renameBook(
            @Min(1) long id,
            @ShellOption(value = {"-n", "--newName"})
            @Size(min = 2, max = 15, message = NAME_LIMIT) String newName
    ) {
        return bookShellService.renameBook(id, newName);
    }

    @ShellMethod(key = "removeBook", value = "Remove a book by id")
    public String removeBook(@Min(1) long id) {
        return bookShellService.removeBook(id);
    }

    @ShellMethod(key = "commentBook", value = "Add book comment")
    public String commentBook(
            @Min(1) long bookId,
            @ShellOption(value = {"-c", "--comment"})
            @Size(min = 2, max = 15, message = NAME_LIMIT) String comment
    ) {
        return bookShellService.addBookComment(bookId, comment);
    }

    @ShellMethod(key = "editBookComment", value = "Edit book comment")
    public String editBookComment(
            @Min(1) long bookId,
            @Min(1) long commentId,
            @ShellOption(value = {"-t", "--text"})
            @Size(min = 2, max = 15, message = NAME_LIMIT) String text
    ) {
        return bookShellService.editBookComment(bookId, commentId, text);
    }

    @ShellMethod(key = "removeBookComment", value = "Remove book comment")
    public String removeBookComment(
            @Min(1) long bookId,
            @Min(1) long commentId
    ) {
        return bookShellService.removeBookComment(bookId, commentId);
    }

    @ShellMethod(key = "addGenre", value = "Add a genre")
    public String addGenre(
            @ShellOption(value = {"-n", "--name"})
            @Size(min = 2, max = 15, message = NAME_LIMIT) String name
    ) {
        return genreShellService.addGenre(name);
    }

    @ShellMethod(key = "showGenre", value = "Show a genre by id")
    public String showGenre(@Min(1) long id) {
        return genreShellService.getGenre(id);
    }

    @ShellMethod(key = "listGenres", value = "Get a list of all genres")
    public String listGenres(
            @ShellOption(value = {"-p", "--page"})
            @Min(0) int page,
            @ShellOption(value = {"-s", "--size"})
            @Min(1) int size
    ) {
        return genreShellService.listAllGenres(page, size);
    }

    @ShellMethod(key = "removeGenre", value = "Remove a genre by id")
    public String removeGenre(@Min(1) long id) {
        return genreShellService.removeGenre(id);
    }

}
