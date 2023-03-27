package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.model.enums.EntityType;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.BookAddService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.GenreService;
import ru.sevryukov.learningspringdb.service.OutputService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookAddServiceImpl implements BookAddService {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final OutputService outputService;

    @Override
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
            bookService.saveBook(new Book(bookName, null, authors, genres));
        }
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
            outputService.printOutput(ex.getMessage());
        }
        return List.of();
    }
}
