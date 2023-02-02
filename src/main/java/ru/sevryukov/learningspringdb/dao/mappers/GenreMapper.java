package ru.sevryukov.learningspringdb.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.sevryukov.learningspringdb.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        var name = rs.getString("naming");
        return new Genre(id, name);
    }
}
