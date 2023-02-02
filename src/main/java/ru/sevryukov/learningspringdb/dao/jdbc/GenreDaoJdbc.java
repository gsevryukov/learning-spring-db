package ru.sevryukov.learningspringdb.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.dao.GenreDao;
import ru.sevryukov.learningspringdb.dao.mappers.GenreMapper;
import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insert(String name) {
        var params = Map.of("naming", name);
        var sql = "insert into genre (\"name\") values (:name)";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Genre getById(long id) {
        var params = Map.of("id", id);
        var sql = "select id, \"name\" from genre where id = :id";
        return namedParameterJdbcTemplate.queryForObject(sql, params, new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        var sql = "select id, \"name\" from genre";
        return namedParameterJdbcTemplate.query(sql, new GenreMapper());
    }

    @Override
    public List<Genre> getAllByIds(List<Long> ids) {
        var params = Map.of("ids", ids.toArray());
        var sql = "select id, \"name\" from genre where id = any(:ids)";
        return namedParameterJdbcTemplate.query(sql, params, new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);
        namedParameterJdbcTemplate.update("delete from genre where id = :id", params);
    }
}
