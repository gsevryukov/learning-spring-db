package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.AuthorShellService;
import ru.sevryukov.learningspringdb.service.UserAskService;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthorShellServiceImpl implements AuthorShellService {

    private final UserAskService userAskService;
    private final AuthorService authorService;

    private static final String MSG_TEMPLATE = "Enter author %s name...";

    @Override
    public void addAuthor() {
        var firstName = userAskService.askUser(String.format(MSG_TEMPLATE, "first"));
        var lastName = userAskService.askUser(String.format(MSG_TEMPLATE, "last"));
        authorService.addAuthor(firstName, lastName);
    }

    @Override
    public void listAllAuthors() {
        printAuthors();
    }

    @Override
    public void removeAuthor() {
        printAuthors();
        var answer = userAskService.askUser("\nEnter author id to remove:");
        try {
            var id = Long.parseLong(answer);
            authorService.deleteAuthor(id);
            System.out.println("Author with id " + id + " was successfully removed");
        } catch (Exception ex) {
            System.out.println("Enter a valid author id!");
            removeAuthor();
        }

    }

    private void printAuthors() {
        var authors = new HashMap<Long, String>();
        authorService.getAll().forEach(v -> authors.put(v.getId(), v.getFirstName() + " " + v.getLastname()));
        System.out.println("ID\tAuthor full name");
        authors.forEach((id, fullName) -> System.out.println(id + "\t" + fullName));
    }

    @Override
    public void printAuthor() {
        var answer = userAskService.askUser("Enter author id...");
        if (answer.equals("exit")) {
            return;
        }
        try {
            var id = Long.parseLong(answer);
            var author = authorService.getById(id);
            System.out.println("ID\tAuthor full name\n"
                    + author.getId() + "\t" + author.getFirstName() + " " + author.getLastname());
        } catch (Exception ex) {
            System.out.println("Enter a valid author id!");
            printAuthor();
        }
    }
}
