package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.service.BookPrinterService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.OutputService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookPrinterServiceImpl implements BookPrinterService {

    private final BookService bookService;
    private final OutputService outputService;
    public static final String BOOK_HEADERS = "ID\t\t" +
            "Book name\t\t" +
            "Book authors\t\t" +
            "Book genres";

    public static final String COMMENT_HEADERS = "ID\t" + "Text\t";

    @Override
    public void printBook(Book book) {
        var headers = BOOK_HEADERS + "\t\tBook comments\n";
        outputService.printOutput(headers + getBookLine(book, true));
    }

    @Override
    public void printAllBooks() {
        var books = bookService.getAll();
        printBooks(books);
    }

    @Override
    public void printBooks(List<Book> books) {
        var headers = BOOK_HEADERS + "\n";
        outputService.printOutput(headers);
        books.forEach(book -> {
            var bookLine = getBookLine(book, false);
            outputService.printOutput(bookLine);
        });
    }

    @Override
    public void printCommentsLine(Book book) {
        if (book.getComments().isEmpty()) {
            return;
        }
        outputService.printOutput(COMMENT_HEADERS);
        book.getComments().forEach(c -> outputService.printOutput(c.getId() + "\t" + c.getText()));
    }

    private String getBookLine(Book book, boolean comments) {
        var authorLine = getAuthorLine(book);
        var genreLine = getGenreLine(book.getGenres().stream().map(Genre::getName).toArray());
        var line = book.getId() + "\t" + book.getName() + "\t" + authorLine + "\t" + genreLine;
        if (comments) {
            var commentLine = getCommentsLine(book);
            line += ("\t" + commentLine);
        }
        return line;
    }

    private String getGenreLine(Object[] book) {
        return Arrays.toString(book);
    }

    private String getCommentsLine(Book book) {
        if (book.getComments().isEmpty()) {
            return "";
        }
        var texts = book.getComments().stream().map(Comment::getText).toArray();
        return Arrays.toString(texts);
    }

    private static String getAuthorLine(Book book) {
        return Arrays
                .toString(
                        book.getAuthors()
                                .stream()
                                .map(a -> a.getFirstName() + " " + a.getLastname())
                                .toArray()
                );
    }


}
