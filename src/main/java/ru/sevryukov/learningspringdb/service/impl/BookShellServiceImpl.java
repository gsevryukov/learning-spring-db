package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.service.BookAddService;
import ru.sevryukov.learningspringdb.service.BookCommentService;
import ru.sevryukov.learningspringdb.service.BookPrinterService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.BookShellService;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class BookShellServiceImpl implements BookShellService {

    private final BookService bookService;
    private final BookAddService bookAddService;
    private final BookCommentService bookCommentService;
    private final BookPrinterService bookPrinterService;
    public static final String NO_BOOK_WITH_ID = "No book with id %s found.";

    @Override
    @Transactional
    public String addBook(String name, List<Long> authorIds, List<Long> genreIds) {
        return bookAddService.addBook(name, authorIds, genreIds);
    }

    @Override
    @Transactional
    public String getBook(long id) {
        var book = bookService.getById(id);
        if (isNull(book)) {
            return String.format(NO_BOOK_WITH_ID, id);
        }
        return bookPrinterService.getBookString(book);
    }

    @Override
    @Transactional
    public String getBooks(int page, int size) {
        var books = bookService.getAll(PageRequest.of(page, size));
        if (books.isEmpty()) {
            return "No books found.";
        }
        return bookPrinterService.getAllBooks(books);
    }

    @Override
    @Transactional
    public String renameBook(long id, String newName) {
        var book = bookService.getById(id);
        if (isNull(book)) {
            return String.format(NO_BOOK_WITH_ID, id);
        }
        book.setName(newName);
        return bookPrinterService.getBookString(bookService.saveBook(book));
    }

    @Override
    @Transactional
    public String removeBook(long id) {
        return bookService.deleteBook(id);
    }

    @Override
    @Transactional
    public String addBookComment(long bookId, String comment) {
        var book = bookService.getById(bookId);
        if (isNull(book)) {
            return String.format(NO_BOOK_WITH_ID, bookId);
        }
        return bookCommentService.addBookComment(book, comment);
    }

    @Override
    @Transactional
    public String editBookComment(long bookId, long commentId, String text) {
        var book = bookService.getById(bookId);
        if (isNull(book)) {
            return String.format(NO_BOOK_WITH_ID, bookId);
        }
        return bookCommentService.editBookComment(book, commentId, text);
    }

    @Override
    @Transactional
    public String removeBookComment(long bookId, long commentId) {
        var book = bookService.getById(bookId);
        if (isNull(book)) {
            return String.format(NO_BOOK_WITH_ID, bookId);
        }
        return bookCommentService.removeBookComment(book, commentId);
    }
}
