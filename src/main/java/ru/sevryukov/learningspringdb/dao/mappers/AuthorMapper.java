package ru.sevryukov.learningspringdb.dao.mappers;

//public class AuthorMapper {

import org.springframework.jdbc.core.RowMapper;
import ru.sevryukov.learningspringdb.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AuthorMapper implements RowMapper<Author> {

    public static Map<String, Object> getMapFromAuthor(Author author) {
        return Map.of(
                "id", author.getId(),
                "first_name", author.getFirstName(),
                "last_name", author.getLastname()
        );
    }

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        var firstName = rs.getString("first_name");
        var lastName = rs.getString("last_name");
        return new Author(id, firstName, lastName);
    }
}
