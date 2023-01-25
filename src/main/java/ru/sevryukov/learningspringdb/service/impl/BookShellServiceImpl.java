package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.BookShellService;
import ru.sevryukov.learningspringdb.service.UserAskService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookShellServiceImpl implements BookShellService {

    private final UserAskService userAskService;
    private final BookService bookService;
    private final AuthorService authorService;
//    private final GenreService genreService;

    @Override
    public void addBook() {
        var bookName = userAskService.askUser("Enter book name");
        var availableAuthorIds = authorService.getAll().stream().map(Author::getId).toList();
        var enteredAuthorIds = getEnteredAuthorIds(availableAuthorIds);
        if (!enteredAuthorIds.isEmpty()) {
            bookService.addBook(bookName, enteredAuthorIds, List.of(1L));
        }

    }

    private List<Long> getEnteredAuthorIds(List<Long> validIds) {
        var result = userAskService.askUser("Enter comma separated author ids:\nValid ids:\n"
                + Arrays.toString(validIds.toArray()));
        try {
            var strings = Arrays.stream(result.split(",")).map(String::trim).toList();
            var ids = strings.stream().map(Long::parseLong).toList();
            var interSection = ids
                    .stream()
                    .filter(validIds::contains)
                    .toList();
            if (interSection.isEmpty()) {
                throw new RuntimeException();
            }
            return ids;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            getEnteredAuthorIds(validIds);
        }
        return List.of();
    }


    @Override
    public void editBook() {

    }

    @Override
    public void listAllBooks() {
        printBooks();
    }

    private void printBooks() {
        var headers = new StringBuilder()
                .append("Book name\t\t")
                .append("Book authors\t\t")
                .append("Book genres\n");
        System.out.println(headers);
        bookService.getAll().forEach(v -> {
            var bookLine = new StringBuilder()
                    .append(v.getName())
                    .append("\t")
                    .append(Arrays.toString(v.getAuthorNames().toArray()))
                    .append("\t")
                    .append(Arrays.toString(v.getGenreNames().toArray()));
            System.out.println(bookLine);
        });
    }

    @Override
    public void removeBook() {

    }

    @Override
    public void printBook() {

    }
}
