package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.BookShellService;
import ru.sevryukov.learningspringdb.service.CommentService;
import ru.sevryukov.learningspringdb.service.GenreService;
import ru.sevryukov.learningspringdb.service.UserAskService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookShellServiceImpl implements BookShellService {

    public static final String BOOK_HEADERS = "ID\t\t" +
            "Book name\t\t" +
            "Book authors\t\t" +
            "Book comment\t\t" +
            "Book genres\n";
    private final UserAskService userAskService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Override
    @Transactional
    public void addBook() {
        var bookName = userAskService.askUser("Enter book name");

        var availableAuthors = authorService
                .getAll()
                .stream()
                .collect(Collectors.toMap(Author::getId, Function.identity()));
        var availableGenres = genreService
                .getAll()
                .stream()
                .collect(Collectors.toMap(Genre::getId, Function.identity()));

        var availableAuthorIds = availableAuthors.keySet().stream().toList();
        var availableGenreIds = availableGenres.keySet().stream().toList();
        var enteredAuthorIds = getEnteredIds(availableAuthorIds, "author");
        var enteredGenreIds = getEnteredIds(availableGenreIds, "genre");

        if (!enteredAuthorIds.isEmpty() && !enteredGenreIds.isEmpty()) {
            var authors = new ArrayList<Author>();
            var genres = new ArrayList<Genre>();
            enteredAuthorIds.forEach(a -> authors.add(availableAuthors.get(a)));
            enteredGenreIds.forEach(a -> genres.add(availableGenres.get(a)));
            bookService.saveBook(new Book(0, bookName, null, authors, genres));
        }

    }

    @Override
    public void printBook() {
        var answer = userAskService.askUser("Enter valid book id...");
        if (answer.equals("exit")) {
            return;
        }
        try {
            long bookId = Long.parseLong(answer);
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
        printAllBooks();
    }

    @Override
    @Transactional
    public void editBook() {
        printAllBooks();
        var answer = userAskService.askUser("\nEnter book id to edit:");
        try {
            long id = Long.parseLong(answer);
            var newName = userAskService.askUser("\nEnter new book name...");
            bookService.editBook(id, newName);
        } catch (Exception ex) {
            System.out.println("Enter a valid book id!");
            editBook();
        }
    }

    @Override
    @Transactional
    public void removeBook() {
        printAllBooks();
        var answer = userAskService.askUser("\nEnter book id to remove:");
        try {
            long id = Long.parseLong(answer);
            bookService.deleteBook(id);
            System.out.println("Book with id " + id + " was successfully removed");
        } catch (Exception ex) {
            System.out.println("Enter a valid book id!");
            removeBook();
        }
    }

    @Override
    @Transactional
    public void addBookComment() {
        var booksWithNoComment = bookService.getAll().stream().filter(b -> b.getComment() == null).toList();
        if (booksWithNoComment.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        printBooks(booksWithNoComment);
        var answer = userAskService.askUser("\nEnter book id to add comment:");
        try {
            addComment(answer);
        } catch (Exception ex) {
            System.out.println("Enter a valid book id!");
            addBookComment();
        }
    }

    @Override
    @Transactional
    public void editBookComment() {
        var booksWithComment = bookService.getAll().stream().filter(b -> b.getComment() != null).toList();
        if (booksWithComment.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        printBooks(booksWithComment);
        var answer = userAskService.askUser("\nEnter book id to edit comment:");
        try {
            addComment(answer);
        } catch (Exception ex) {
            System.out.println("Enter a valid book id!");
            editBookComment();
        }
    }

    @Override
    @Transactional
    public void removeBookComment() {
        var booksWithComment = bookService.getAll().stream().filter(b -> b.getComment() != null).toList();
        if (booksWithComment.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        printBooks(booksWithComment);
        var answer = userAskService.askUser("\nEnter book id to remove comment:");
        try {
            long id = Long.parseLong(answer);
            var book = bookService.getById(id);
            long commentId = book.getComment().getId();
            book.setComment(null);
            bookService.saveBook(book);
            commentService.deleteComment(commentId);
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Enter a valid book id!");
            removeBookComment();
        }
    }

    private void addComment(String answer) {
        long id = Long.parseLong(answer);
        var comment = userAskService.askUser("\nEnter your comment...");
        var b = bookService.getById(id);
        b.setComment(new Comment(0, comment));
        bookService.saveBook(b);
    }

    private void printAllBooks() {
        var books = bookService.getAll();
        printBooks(books);
    }

    private void printBooks(List<Book> books) {
        System.out.println(BOOK_HEADERS);
        books.forEach(book -> {
            var bookLine = getBookLine(book);
            System.out.println(bookLine);
        });
    }

    private static String getBookLine(Book book) {
        var authorLine = getAuthorLine(book);
        var genreLine = getGenreLine(book.getGenres().stream().map(Genre::getName).toArray());
        var commentLine = getCommentLine(book);
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

    private static String getGenreLine(Object[] book) {
        return Arrays.toString(book);
    }

    private static String getCommentLine(Book book) {
        return book.getComment() == null ? "" : book.getComment().getText();
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

    private List<Long> getEnteredIds(List<Long> validIds, String type) {
        var result = userAskService.askUser(String.format("Enter comma separated %s ids:\nValid ids:\n", type)
                + getGenreLine(validIds.toArray()));
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
