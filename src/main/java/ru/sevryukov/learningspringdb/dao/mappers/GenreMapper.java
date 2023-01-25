package ru.sevryukov.learningspringdb.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.sevryukov.learningspringdb.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GenreMapper implements RowMapper<Genre> {

    public static Map<String, Object> getMapFromGenre(Genre genre) {
        return Map.of(
                "id", genre.getId(),
                "name", genre.getName()
        );
    }

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        var name = rs.getString("name");
        return new Genre(id, name);
    }
}
