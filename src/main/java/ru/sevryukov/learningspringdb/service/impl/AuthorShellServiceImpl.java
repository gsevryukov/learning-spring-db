package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.AuthorShellService;
import ru.sevryukov.learningspringdb.service.EntityService;
import ru.sevryukov.learningspringdb.service.UserAskService;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthorShellServiceImpl implements AuthorShellService {

    public static final String AUTHOR_HEADER = "ID\tAuthor full name";
    private final UserAskService userAskService;
    private final EntityService entityService;

    private static final String MSG_TEMPLATE = "Enter author %s name...";

    @Override
    public void addAuthor() {
        var firstName = userAskService.askUser(String.format(MSG_TEMPLATE, "first"));
        var lastName = userAskService.askUser(String.format(MSG_TEMPLATE, "last"));
        entityService.addAuthor(firstName, lastName);
    }

    @Override
    public void printAuthor() {
        var answer = userAskService.askUser("Enter author id...");
        if (answer.equals("exit")) {
            return;
        }
        try {
            var author = entityService.getAuthorById(answer);
            System.out.println(AUTHOR_HEADER
                    + "\n" + author.getId() + "\t" + author.getFirstName() + " " + author.getLastname());
        } catch (Exception ex) {
            System.out.println("Enter a valid author id!");
            printAuthor();
        }
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
            entityService.removeAuthor(answer);
            System.out.println("Author with id " + answer + " was successfully removed");
        } catch (Exception ex) {
            System.out.println("Enter a valid author id!");
            removeAuthor();
        }
    }

    private void printAuthors() {
        var authors = new HashMap<Long, String>();
        entityService.getAllAuthors().forEach(v -> authors.put(v.getId(), v.getFirstName() + " " + v.getLastname()));
        System.out.println(AUTHOR_HEADER);
        authors.forEach((id, fullName) -> System.out.println(id + "\t" + fullName));
    }

}
