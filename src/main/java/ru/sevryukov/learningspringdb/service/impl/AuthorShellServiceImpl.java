package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.AuthorShellService;
import ru.sevryukov.learningspringdb.service.OutputService;
import ru.sevryukov.learningspringdb.service.UserAskService;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class AuthorShellServiceImpl implements AuthorShellService {

    private final OutputService outputService;
    private final AuthorService authorService;
    private final UserAskService userAskService;
    public static final String AUTHOR_HEADER = "ID\tAuthor full name";
    private static final String MSG_TEMPLATE = "Enter author %s name...";

    @Override
    public void addAuthor() {
        var firstName = userAskService.askUser(String.format(MSG_TEMPLATE, "first"));
        var lastName = userAskService.askUser(String.format(MSG_TEMPLATE, "last"));
        authorService.addAuthor(firstName, lastName);
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
            if (isNull(author)) {
                throw new EntityNotFoundException(String.format("No author with id %s found", id));
            }
            outputService.printOutput(AUTHOR_HEADER
                    + "\n" + author.getId() + "\t" + author.getFirstName() + " " + author.getLastname());
        } catch (Exception ex) {
            outputService.printOutput("Enter a valid author id!");
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
            var id = Long.parseLong(answer);
            authorService.deleteAuthor(id);
            outputService.printOutput("Author with id " + answer + " was successfully removed");
        } catch (Exception ex) {
            outputService.printOutput("Enter a valid author id!");
            removeAuthor();
        }
    }

    private void printAuthors() {
        var authors = new HashMap<Long, String>();
        authorService.getAll().forEach(v -> authors.put(v.getId(), v.getFirstName() + " " + v.getLastname()));
        outputService.printOutput(AUTHOR_HEADER);
        authors.forEach((id, fullName) -> outputService.printOutput(id + "\t" + fullName));
    }

}
