package ru.sevryukov.learningspringdb.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.sevryukov.learningspringdb.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        var firstName = rs.getString("first_name");
        var lastName = rs.getString("last_name");
        return new Author(id, firstName, lastName);
    }
}
