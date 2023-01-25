package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.sevryukov.learningspringdb.service.AuthorShellService;
import ru.sevryukov.learningspringdb.service.BookShellService;

@ShellComponent
@RequiredArgsConstructor
public class ShellRunnerServiceImpl {

    private final AuthorShellService authorShellService;
    private final BookShellService bookShellService;

    @ShellMethod(key = "addAuthor", value = "Add an author")
    public void addAuthor() {
        authorShellService.addAuthor();
    }

    @ShellMethod(key = "listAuthors", value = "Get a list of all authors")
    public void listAuthors() {
        authorShellService.listAllAuthors();
    }

    @ShellMethod(key = "removeAuthor", value = "Remove an author by id")
    public void removeAuthor() {
        authorShellService.removeAuthor();
    }

    @ShellMethod(key = "showAuthor", value = "Show an author by id")
    public void showAuthor() {
        authorShellService.printAuthor();
    }

    @ShellMethod(key = "addBook", value = "Add a book")
    public void addBook() {
        bookShellService.addBook();
    }

    @ShellMethod(key = "listBooks", value = "Get a list of all books")
    public void listBooks() {
        bookShellService.listAllBooks();
    }

//    @ShellMethod(key = "addGenre", value = "Add a genre")
//    public void addGenre() {
//    }
//
//    @ShellMethod(key = "listGenres", value = "Get a list of all genres")
//    public void listGenres() {
//        System.out.println(Arrays.toString(genreService.getAll().toArray()));
//    }

}
