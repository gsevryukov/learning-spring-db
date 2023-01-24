package ru.sevryukov.learningspringdb.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.BookEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

public class BookMapper implements RowMapper<Book> {

    public static Map<String, Object> getMapFromBook(BookEntity author) {
        return Map.of(
                "id", author.getId(),
                "name", author.getBookName(),
                "author_id", author.getAuthorId(),
                "genre_id", author.getGenreId()
        );
    }

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        var bookName = rs.getString("book_name");
        var authorNames = Arrays.stream(rs.getString("full_names").split(",")).toList();
        var genreNames = Arrays.stream(rs.getString("genre_names").split(",")).toList();
        return new Book(id, bookName, authorNames, genreNames);
    }
}
