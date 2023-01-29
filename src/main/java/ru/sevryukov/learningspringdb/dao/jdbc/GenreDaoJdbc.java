package ru.sevryukov.learningspringdb.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.dao.GenreDao;
import ru.sevryukov.learningspringdb.dao.mappers.GenreMapper;
import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(String name) {
        var params = Map.of("name", name);
        var sql = "insert into genre (\"name\") values (:name)";
        namedParameterJdbcOperations.update(sql, params);
    }

    @Override
    public Genre getById(long id) {
        var params = Map.of("id", id);
        var sql = "select id, \"name\" from genre where id = :id";
        return namedParameterJdbcOperations.queryForObject(sql, params, new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        var sql = "select id, \"name\" from genre";
        return namedParameterJdbcOperations.query(sql, new GenreMapper());
    }

    @Override
    public List<Genre> getAllByIds(List<Long> ids) {
        var params = Map.of("ids", ids.toArray());
        var sql = "select id, \"name\" from genre where id = any(:ids)";
        return namedParameterJdbcOperations.query(sql, params, new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);
        namedParameterJdbcOperations.update("delete from genre where id = :id", params);
    }
}
