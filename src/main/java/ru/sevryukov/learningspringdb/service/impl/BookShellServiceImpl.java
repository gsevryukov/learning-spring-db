package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.BookShellService;
import ru.sevryukov.learningspringdb.service.GenreService;
import ru.sevryukov.learningspringdb.service.UserAskService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookShellServiceImpl implements BookShellService {

    public static final String BOOK_HEADERS = "ID\t\t" +
            "Book name\t\t" +
            "Book authors\t\t" +
            "Book genres\n";
    private final UserAskService userAskService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public void addBook() {
        var bookName = userAskService.askUser("Enter book name");
        var availableAuthorIds = authorService.getAll().stream().map(Author::getId).toList();
        var availableGenreIds = genreService.getAll().stream().map(Genre::getId).toList();
        var enteredAuthorIds = getEnteredIds(availableAuthorIds, "author");
        var enteredGenreIds = getEnteredIds(availableGenreIds, "genre");
        if (!enteredAuthorIds.isEmpty() && !enteredGenreIds.isEmpty()) {
            bookService.addBook(bookName, enteredAuthorIds, enteredGenreIds);
        }

    }

    @Override
    public void printBook() {
        var answer = userAskService.askUser("Enter valid book id...");
        if (answer.equals("exit")) {
            return;
        }
        try {
            var bookId = Long.parseLong(answer);
            var availableIds = bookService.getAll().stream().map(Book::getId).toList();
            if (!availableIds.contains(bookId)) {
                throw new RuntimeException();
            }
            var book = bookService.getById(bookId);
            System.out.println(BOOK_HEADERS);
            System.out.println(getBookLine(book));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            printBook();
        }
    }

    @Override
    public void listAllBooks() {
        printBooks();
    }

    @Override
    public void editBook() {
        printBooks();
        var answer = userAskService.askUser("\nEnter book id to edit:");
        try {
            var id = Long.parseLong(answer);
            var newName = userAskService.askUser("\nEnter new book name...");
            var availableAuthorIds = authorService.getAll().stream().map(Author::getId).toList();
            var availableGenreIds = genreService.getAll().stream().map(Genre::getId).toList();
            var enteredAuthorIds = getEnteredIds(availableAuthorIds, "author");
            var enteredGenreIds = getEnteredIds(availableGenreIds, "genre");
            bookService.editBook(id, newName, enteredAuthorIds, enteredGenreIds);
        } catch (Exception ex) {
            System.out.println("Enter a valid book id!");
            editBook();
        }
    }

    @Override
    public void removeBook() {
        printBooks();
        var answer = userAskService.askUser("\nEnter book id to remove:");
        try {
            var id = Long.parseLong(answer);
            bookService.deleteBook(id);
            System.out.println("Book with id " + id + " was successfully removed");
        } catch (Exception ex) {
            System.out.println("Enter a valid book id!");
            removeBook();
        }
    }

    private void printBooks() {
        System.out.println(BOOK_HEADERS);
        bookService.getAll().forEach(v -> {
            var bookLine = getBookLine(v);
            System.out.println(bookLine);
        });
    }

    private static String getBookLine(Book book) {
        return book.getId() +
                "\t" +
                book.getName() +
                "\t" +
                Arrays.toString(book.getAuthorNames().toArray()) +
                "\t" +
                Arrays.toString(book.getGenreNames().toArray());
    }

    private List<Long> getEnteredIds(List<Long> validIds, String type) {
        var result = userAskService.askUser(String.format("Enter comma separated %s ids:\nValid ids:\n", type)
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
            getEnteredIds(validIds, type);
        }
        return List.of();
    }


}
