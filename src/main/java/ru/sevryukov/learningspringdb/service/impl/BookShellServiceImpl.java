package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.model.enums.EntityType;
import ru.sevryukov.learningspringdb.service.BookShellService;
import ru.sevryukov.learningspringdb.service.EntityService;
import ru.sevryukov.learningspringdb.service.UserAskService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookShellServiceImpl implements BookShellService {

    public static final String BOOK_HEADERS = "ID\t\t" +
            "Book name\t\t" +
            "Book authors\t\t" +
            "Book comments\t\t" +
            "Book genres\n";

    public static final String COMMENT_HEADERS = "ID\t" + "Text\t";
    public static final String ENTER_A_VALID_BOOK_ID = "Enter a valid book id!";

    private final UserAskService userAskService;

    private final EntityService entityService;

    @Override
    public void addBook() {
        var bookName = userAskService.askUser("Enter book name");
        var authorIds = askForAuthorIds();
        var genreIds = askForGenreIds();
        entityService.addBook(bookName, authorIds, genreIds);
    }

    @Override
    public void printBook() {
        var answer = userAskService.askUser(ENTER_A_VALID_BOOK_ID);
        if (answer.equals("exit")) {
            return;
        }
        try {
            var book = entityService.getBookById(answer);
            System.out.println(BOOK_HEADERS);
            System.out.println(getBookLine(book));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            printBook();
        }
    }

    @Override
    public void listAllBooks() {
        printAllBooks();
    }

    @Override
    public void editBook() {
        printAllBooks();
        var answer = userAskService.askUser("\nEnter book id to edit:");
        try {
            long id = Long.parseLong(answer);
            var newName = userAskService.askUser("\nEnter new book name...");
            entityService.renameBook(id, newName);
        } catch (Exception ex) {
            System.out.println(ENTER_A_VALID_BOOK_ID);
            editBook();
        }
    }

    @Override
    public void removeBook() {
        printAllBooks();
        var answer = userAskService.askUser("\nEnter book id to remove:");
        try {
            long id = Long.parseLong(answer);
            entityService.deleteBook(id);
            System.out.println("Book with id " + id + " was successfully removed");
        } catch (Exception ex) {
            System.out.println(ENTER_A_VALID_BOOK_ID);
            removeBook();
        }
    }

    @Override
    public void addBookComment() {
        printAllBooks();
        var answer = userAskService.askUser("\nEnter book id to add comment:");
        try {
            long bookId = Long.parseLong(answer);
            var commentText = userAskService.askUser("\nEnter your comment...");
            entityService.addBookComment(bookId, commentText);
        } catch (Exception ex) {
            System.out.println(ENTER_A_VALID_BOOK_ID + ex);
            addBookComment();
        }
    }

    @Override
    public void editBookComment() {
        var booksWithComment = entityService.getAllBooks().stream().filter(b -> b.getComments() != null).toList();
        if (booksWithComment.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        printBooks(booksWithComment);
        var answer = userAskService.askUser("\nEnter book id to edit comment:");
        try {
            var book = entityService.getBookById(answer);
            printCommentsLine(book);
            var commentIdStr = userAskService.askUser("\nEnter comment id to edit:");
            long id = Long.parseLong(commentIdStr);
            var text = userAskService.askUser("\nEnter text:");
            entityService.editCommentById(id, text);
        } catch (Exception ex) {
            System.out.println("Enter a valid data!");
            editBookComment();
        }
    }

    @Override
    public void removeBookComment() {
        var booksWithComment = getBooksWithComment();
        if (booksWithComment.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        printBooks(booksWithComment);
        var answer = userAskService.askUser("\nEnter book id to remove comment:");
        try {
            var book = entityService.getBookById(answer);
            printCommentsLine(book);
            var commentIdStr = userAskService.askUser("\nEnter comment id:");
            long commentId = Long.parseLong(commentIdStr);
            entityService.deleteCommentById(book, commentId);
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Enter a valid data!");
            removeBookComment();
        }
    }

    private List<Book> getBooksWithComment() {
        return entityService.getAllBooks().stream().filter(b -> !b.getComments().isEmpty()).toList();
    }

    private void printAllBooks() {
        var books = entityService.getAllBooks();
        printBooks(books);
    }

    private void printBooks(List<Book> books) {
        System.out.println(BOOK_HEADERS);
        books.forEach(book -> {
            var bookLine = getBookLine(book);
            System.out.println(bookLine);
        });
    }

    private String getBookLine(Book book) {
        var authorLine = getAuthorLine(book);
        var genreLine = getGenreLine(book.getGenres().stream().map(Genre::getName).toArray());
        var commentLine = getCommentsLine(book);
        return book.getId() +
                "\t" +
                book.getName() +
                "\t"
                + commentLine +
                "\t" +
                authorLine +
                "\t" +
                genreLine;
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

    private void printCommentsLine(Book book) {
        if (book.getComments().isEmpty()) {
            return;
        }
        System.out.println(COMMENT_HEADERS);
        book.getComments().forEach(c -> System.out.println(c.getId() + "\t" + c.getText()));
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

    private List<Long> askForIds(EntityType type) {
        var t = type.name().toLowerCase();
        var result = userAskService.askUser(String.format("Enter comma separated %s ids:\n", t));
        var strings = Arrays.stream(result.split(",")).map(String::trim).toList();
        return strings.stream().map(Long::parseLong).toList();
    }

    private List<Long> askForAuthorIds() {
        try {
            var ids = entityService.getAllAuthors().stream().map(Author::getId).toArray();
            System.out.println("\nValid ids: " + Arrays.toString(ids));
            return askForIds(EntityType.AUTHOR);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return askForAuthorIds();
        }
    }

    private List<Long> askForGenreIds() {
        try {
            var ids = entityService.getAllGenres().stream().map(Genre::getId).toArray();
            System.out.println("\nValid ids: " + Arrays.toString(ids));
            return askForIds(EntityType.GENRE);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return askForGenreIds();
        }
    }
}
