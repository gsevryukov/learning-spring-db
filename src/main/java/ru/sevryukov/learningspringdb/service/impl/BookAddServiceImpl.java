package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.BookAddService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookAddServiceImpl implements BookAddService {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Override
    public String addBook(String bookName,
                          List<Long> enteredAuthorIds,
                          List<Long> enteredGenreIds) {
        var authors = authorService.getAllByIds(enteredAuthorIds);
        if (authors.isEmpty()) {
            return "No authors found by entered ids";
        }
        var genres = genreService.getAllByIds(enteredGenreIds);
        if (genres.isEmpty()) {
            return "No genres found by entered ids";
        }
        bookService.saveBook(new Book(bookName, authors, genres));
        return "Book saved.";
    }

}
