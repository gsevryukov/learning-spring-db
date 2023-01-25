package ru.sevryukov.learningspringdb.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.dao.AuthorDao;
import ru.sevryukov.learningspringdb.dao.mappers.AuthorMapper;
import ru.sevryukov.learningspringdb.model.Author;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcTemplate namedParametersJdbcTemplate;

    @Override
    public void insert(String firstName, String lastName) {
        var params = Map.of("first_name", firstName, "last_name", lastName);
        var sql = "insert into author (first_name, last_name) values (:first_name, :last_name)";
        namedParametersJdbcTemplate.update(sql, params);
    }

    @Override
    public List<Author> getAll() {
        var sql = "select id, first_name, last_name from author";
        return namedParametersJdbcTemplate.query(sql, new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        var params = Map.of("id", id);
        var sql = "select id, first_name, last_name from author where id = :id";
        return namedParametersJdbcTemplate.queryForObject(sql, params, new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);
        namedParametersJdbcTemplate.update("delete from author where id = :id", params);
    }


}
