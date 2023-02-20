package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.model.enums.EntityType;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.CommentService;
import ru.sevryukov.learningspringdb.service.EntityService;
import ru.sevryukov.learningspringdb.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;

    @Override
    @Transactional
    public void addBook(String bookName,
                        List<Long> enteredAuthorIds,
                        List<Long> enteredGenreIds) {

        var availableAuthors = authorService
                .getAll()
                .stream()
                .collect(Collectors.toMap(Author::getId, Function.identity()));
        var availableGenres = genreService
                .getAll()
                .stream()
                .collect(Collectors.toMap(Genre::getId, Function.identity()));

        var validEnteredAuthorIds
                = getValidEnteredIds(enteredAuthorIds, EntityType.AUTHOR, availableAuthors, availableGenres);
        var validEnteredGenreIds
                = getValidEnteredIds(enteredGenreIds, EntityType.GENRE, availableAuthors, availableGenres);

        if (!validEnteredAuthorIds.isEmpty() && !validEnteredGenreIds.isEmpty()) {
            var authors = new ArrayList<Author>();
            var genres = new ArrayList<Genre>();
            validEnteredAuthorIds.forEach(a -> authors.add(availableAuthors.get(a)));
            validEnteredGenreIds.forEach(a -> genres.add(availableGenres.get(a)));
            bookService.saveBook(new Book(0, bookName, null, authors, genres));
        }
    }

    @Override
    public Book getBookById(String enteredId) {
        long bookId = Long.parseLong(enteredId);
        var availableIds = bookService.getAll().stream().map(Book::getId).toList();
        if (!availableIds.contains(bookId)) {
            throw new RuntimeException();
        }
        return bookService.getById(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @Override
    public void renameBook(Long bookId, String newName) {
        bookService.editBook(bookId, newName);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookService.deleteBook(bookId);
    }

    @Override
    @Transactional
    public void addBookComment(long bookId, String text) {
        var b = bookService.getById(bookId);
        var comments = b.getComments();
        var c = commentService.saveComment(new Comment(0, text));
        comments.add(c);
        bookService.saveBook(b);
    }

    @Override
    @Transactional
    public void editCommentById(long commentId, String text) {
        commentService.editComment(commentId, text);
    }

    @Override
    @Transactional
    public void deleteCommentById(Book book, long commentId) {
        book.getComments().stream().filter(v -> v.getId() == commentId).findFirst().ifPresent(
                comment -> {
                    book.getComments().remove(comment);
                    bookService.saveBook(book);
                }
        );
        commentService.deleteComment(commentId);
    }

    @Override
    @Transactional
    public void addAuthor(String firstName, String lastName) {
        authorService.addAuthor(firstName, lastName);
    }

    @Override
    public Author getAuthorById(String enteredId) {
        long id = Long.parseLong(enteredId);
        return authorService.getById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorService.getAll();
    }

    @Override
    @Transactional
    public void removeAuthor(String enteredId) {
        long id = Long.parseLong(enteredId);
        authorService.deleteAuthor(id);
    }

    @Override
    @Transactional
    public void addGenre(String name) {
        genreService.addGenre(name);
    }

    @Override
    public Genre getGenreById(String enteredId) {
        long id = Long.parseLong(enteredId);
        return genreService.getById(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @Override
    @Transactional
    public void removeGenre(String enteredId) {
        long id = Long.parseLong(enteredId);
        genreService.deleteById(id);
    }

    private List<Long> getValidEnteredIds(List<Long> enteredIds,
                                          EntityType type,
                                          Map<Long, Author> availableAuthors,
                                          Map<Long, Genre> availableGenres) {
        List<Long> validIds = new ArrayList<>();
        if (type == EntityType.AUTHOR) {
            validIds = availableAuthors.keySet().stream().toList();
        } else if (type == EntityType.GENRE) {
            validIds = availableGenres.keySet().stream().toList();
        }
        try {
            var interSection = enteredIds
                    .stream()
                    .filter(validIds::contains)
                    .toList();
            if (interSection.isEmpty()) {
                throw new RuntimeException();
            }
            return enteredIds;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return List.of();
    }
}
