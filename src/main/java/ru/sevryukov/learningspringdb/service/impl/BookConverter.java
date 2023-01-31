package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.BookEntity;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookConverter {

    private final GenreService genreService;

    private final AuthorService authorService;

    public Book getBookFromBookEntity(BookEntity bookEntity) {
        var genres = getGenreNames(bookEntity.getGenreIds());
        var authorNames = getAuthorNames(bookEntity.getGenreIds());
        return new Book(bookEntity.getId(), bookEntity.getBookName(), authorNames, genres);
    }

    private List<String> getGenreNames(List<Long> ids) {
        var genres = genreService.getAllByIds(ids);
        return genres.stream().map(Genre::getName).toList();
    }

    private List<String> getAuthorNames(List<Long> ids) {
        var authors = authorService.getAllByIds(ids);
        var result = new ArrayList<String>();
        authors.forEach(v -> result.add(v.getFirstName() + " " + v.getLastname()));
        return result;
    }
}
