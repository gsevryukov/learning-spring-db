package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.service.BookCommentService;
import ru.sevryukov.learningspringdb.service.BookPrinterService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.CommentService;
import ru.sevryukov.learningspringdb.service.OutputService;
import ru.sevryukov.learningspringdb.service.UserAskService;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final BookService bookService;
    private final OutputService outputService;
    private final UserAskService userAskService;
    private final CommentService commentService;
    private final BookPrinterService bookPrinterService;
    public static final String ENTER_BOOK_NAME = "Enter book name";
    public static final String ENTER_A_VALID_BOOK_ID = "Enter a valid book id!";

    @Override
    public void addBookComment() {
        var answer = userAskService.askUser("\nEnter book id to add comment:");
        try {
            long bookId = Long.parseLong(answer);
            var b = bookService.getById(bookId);
            var commentText = userAskService.askUser("\nEnter your comment...");
            var comments = b.getComments();
            var c = commentService.saveComment(new Comment(commentText));
            comments.add(c);
            bookService.saveBook(b);
        } catch (Exception ex) {
            outputService.printOutput(ENTER_A_VALID_BOOK_ID + ex);
            addBookComment();
        }
    }

    public void editBookComment() {
        var bookName = userAskService.askUser(ENTER_BOOK_NAME);
        var booksWithComment = bookService.getAllCommentedByName(bookName);
        if (booksWithComment.isEmpty()) {
            outputService.printOutput("No books found");
            return;
        }
        bookPrinterService.printBooks(booksWithComment);
        var answer = userAskService.askUser("\nEnter book id to edit comment:");
        try {
            var bookId = Long.parseLong(answer);
            var book = booksWithComment.stream().filter(b -> b.getId() == bookId).findFirst().orElseThrow();
            bookPrinterService.printCommentsLine(book);
            var commentIdStr = userAskService.askUser("\nEnter comment id to edit:");
            long commentId = Long.parseLong(commentIdStr);
            var text = userAskService.askUser("\nEnter text:");
            var commentToEdit = book.getComments().stream().filter(c -> c.getId() == commentId).findFirst().orElseThrow();
            commentToEdit.setText(text);
        } catch (Exception ex) {
            outputService.printOutput("Enter a valid data!");
            editBookComment();
        }
    }

    @Override
    @Transactional
    public void removeBookComment() {
        var bookName = userAskService.askUser(ENTER_BOOK_NAME);
        var booksWithComment = bookService.getAllCommentedByName(bookName);
        if (booksWithComment.isEmpty()) {
            outputService.printOutput("No books found");
            return;
        }
        bookPrinterService.printBooks(booksWithComment);
        var answer = userAskService.askUser("\nEnter book id to remove comment:");
        try {
            var bookId = Long.parseLong(answer);
            var book = booksWithComment.stream().filter(b -> b.getId() == bookId).findFirst().orElseThrow();
            bookPrinterService.printCommentsLine(book);
            var commentIdStr = userAskService.askUser("\nEnter comment id:");
            long commentId = Long.parseLong(commentIdStr);
            deleteCommentById(book, commentId);
        } catch (Exception ex) {
            outputService.printOutput(ex.toString());
            outputService.printOutput("Enter a valid data!");
            removeBookComment();
        }
    }

    private void deleteCommentById(Book book, long commentId) {
        book.getComments().stream().filter(v -> v.getId() == commentId).findFirst().ifPresent(
                comment -> {
                    book.getComments().remove(comment);
                    bookService.saveBook(book);
                }
        );
        commentService.deleteComment(commentId);
    }
}
