package ru.sevryukov.learningspringdb.dao.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Book;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        var bookName = rs.getString("name");
        var genreIdStr = rs.getArray("genre_names");
        var authorIdStr = rs.getArray("author_names");
        return new Book(id, bookName, getNamesFromStr(authorIdStr), getNamesFromStr(genreIdStr));
    }

    private List<String> getNamesFromStr(Array listStr) {
        try {
            var arr = (Object[]) listStr.getArray();
            if (arr.length == 0) {
                return List.of();
            }
            return Arrays.stream(arr).map(String.class::cast).toList();

        } catch (Exception ex) {
            System.out.println("Error while getting values from array" + ex);
            return List.of();
        }

    }

}
