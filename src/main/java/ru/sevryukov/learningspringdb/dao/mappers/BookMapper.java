package ru.sevryukov.learningspringdb.dao.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.BookEntity;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.GenreService;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookMapper implements RowMapper<Book> {

    private final GenreService genreService;

    private final AuthorService authorService;

    public static Map<String, Object> getMapFromBook(BookEntity bookEntity) {
        return Map.of(
                "name", bookEntity.getBookName(),
                "author_ids", bookEntity.getAuthorIds().toArray(),
                "genre_ids", bookEntity.getGenreIds().toArray()
        );
    }

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        var bookName = rs.getString("name");
        var genreIdStr = rs.getArray("genre_ids");
        var authorIdStr = rs.getArray("author_ids");
        var genreNames = getGenreNames(genreIdStr);
        var authorNames = getAuthorNames(authorIdStr);
        return new Book(id, bookName, authorNames, genreNames);
    }

    private List<String> getGenreNames(Array genreString) {
        var ids = getIdsFromStr(genreString);
        var genres = genreService.getAllByIds(ids);
        return genres.stream().map(Genre::getName).toList();
    }

    private List<String> getAuthorNames(Array authorStr) {
        var ids = getIdsFromStr(authorStr);
        var authors = authorService.getAllByIds(ids);
        var result = new ArrayList<String>();
        authors.forEach(v -> result.add(v.getFirstName() + " " + v.getLastname()));
        return result;
    }

    private List<Long> getIdsFromStr(Array listStr) {
        try {
            var arr = (Object[]) listStr.getArray();
            if (arr.length == 0) {
                return List.of();
            }
            return Arrays.stream(arr).map(Long.class::cast).toList();

        } catch (Exception ex) {
            System.out.println("Error while getting an ids array" + ex);
            return List.of();
        }

    }
}
