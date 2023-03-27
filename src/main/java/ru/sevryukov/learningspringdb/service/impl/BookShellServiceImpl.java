package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.enums.EntityType;
import ru.sevryukov.learningspringdb.service.BookAddService;
import ru.sevryukov.learningspringdb.service.BookCommentService;
import ru.sevryukov.learningspringdb.service.BookPrinterService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.BookShellService;
import ru.sevryukov.learningspringdb.service.OutputService;
import ru.sevryukov.learningspringdb.service.UserAskService;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookShellServiceImpl implements BookShellService {

    private final BookService bookService;
    private final OutputService outputService;
    private final UserAskService userAskService;
    private final BookAddService bookAddService;
    private final BookCommentService bookCommentService;
    private final BookPrinterService bookPrinterService;
    public static final String ENTER_BOOK_NAME = "Enter book name";
    public static final String ENTER_A_VALID_BOOK_ID = "Enter a valid book id!";

    @Override
    @Transactional
    public void addBook() {
        var bookName = userAskService.askUser(ENTER_BOOK_NAME);
        var authorIds = askForAuthorIds();
        var genreIds = askForGenreIds();
        bookAddService.addBook(bookName, authorIds, genreIds);
    }

    @Override
    @Transactional
    public void printBook() {
        var answer = userAskService.askUser(ENTER_A_VALID_BOOK_ID);
        if (answer.equals("exit")) {
            return;
        }
        try {
            long bookId = Long.parseLong(answer);
            var book = bookService.getById(bookId);
            if (book == null) {
                throw new EntityNotFoundException(String.format("No book with id %s found", bookId));
            }
            bookPrinterService.printBook(book);
        } catch (Exception ex) {
            outputService.printOutput(ex.getMessage());
            printBook();
        }
    }

    @Override
    @Transactional
    public void listAllBooks() {
        bookPrinterService.printAllBooks();
    }

    @Override
    @Transactional
    public void editBook() {
        var answer = userAskService.askUser("\nEnter book id to edit:");
        try {
            long id = Long.parseLong(answer);
            var newName = userAskService.askUser("\nEnter new book name...");
            bookService.editBook(id, newName);
        } catch (Exception ex) {
            outputService.printOutput(ENTER_A_VALID_BOOK_ID);
            editBook();
        }
    }

    @Override
    @Transactional
    public void removeBook() {
        var answer = userAskService.askUser("\nEnter book id to remove:");
        try {
            long id = Long.parseLong(answer);
            bookService.deleteBook(id);
            outputService.printOutput("Book with id " + id + " was successfully removed");
        } catch (Exception ex) {
            outputService.printOutput(ENTER_A_VALID_BOOK_ID);
            removeBook();
        }
    }

    @Override
    @Transactional
    public void addBookComment() {
        bookCommentService.addBookComment();
    }

    @Override
    @Transactional
    public void editBookComment() {
        bookCommentService.editBookComment();
    }

    @Override
    @Transactional
    public void removeBookComment() {
        bookCommentService.removeBookComment();
    }

    private List<Long> askForIds(EntityType type) {
        var t = type.name().toLowerCase();
        var result = userAskService.askUser(String.format("Enter comma separated %s ids:%n", t));
        var strings = Arrays.stream(result.split(",")).map(String::trim).toList();
        return strings.stream().map(Long::parseLong).toList();
    }

    private List<Long> askForAuthorIds() {
        try {
            return askForIds(EntityType.AUTHOR);
        } catch (Exception ex) {
            outputService.printOutput(ex.getMessage());
            return askForAuthorIds();
        }
    }

    private List<Long> askForGenreIds() {
        try {
            return askForIds(EntityType.GENRE);
        } catch (Exception ex) {
            outputService.printOutput(ex.getMessage());
            return askForGenreIds();
        }
    }
}
