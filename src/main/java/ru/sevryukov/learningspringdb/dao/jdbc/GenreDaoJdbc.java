package ru.sevryukov.learningspringdb.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.sevryukov.learningspringdb.dao.GenreDao;
import ru.sevryukov.learningspringdb.dao.mappers.GenreMapper;
import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(Genre genre) {
        var map = GenreMapper.getMapFromGenre(genre);
        var sql = "insert into genre (\"name\") values (:name)";
        namedParameterJdbcOperations.update(sql, map);
    }

    @Override
    public Genre getById(long id) {
        return null;
    }

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public List<Long> getAllIds() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
