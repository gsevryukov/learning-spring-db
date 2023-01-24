package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.sevryukov.learningspringdb.service.AuthorAddService;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.ShellRunnerService;

import java.util.Arrays;

@ShellComponent
@RequiredArgsConstructor
public class ShellRunnerServiceImpl implements ShellRunnerService {

    private final AuthorAddService authorAddService;
    private final AuthorService authorService;

    @Override
    @ShellMethod(key = "addAuthor", value = "Add author")
    public void runInShell() {
        authorAddService.addAuthor();
    }

    @ShellMethod(key = "listAuthors", value = "Get a list of all authors")
    public void listAuthors() {
        System.out.println(Arrays.toString(authorService.getAll().toArray()));
    }

}
