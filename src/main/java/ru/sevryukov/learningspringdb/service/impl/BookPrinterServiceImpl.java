package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.service.BookPrinterService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookPrinterServiceImpl implements BookPrinterService {

    public static final String BOOK_HEADERS = "ID\t\t" +
            "Book name\t\t" +
            "Book authors\t\t" +
            "Book genres";

    @Override
    public String getBookString(Book book) {
        return BOOK_HEADERS + "\t\tBook comments\n" + getBookLine(book, true);
    }

    @Override
    public String getAllBooks(List<Book> books) {
        var sb = new StringBuilder(BOOK_HEADERS + "\n");
        books.forEach(book -> {
            var bookLine = getBookLine(book, false);
            sb.append(bookLine).append("\n");
        });
        return sb.toString();
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
