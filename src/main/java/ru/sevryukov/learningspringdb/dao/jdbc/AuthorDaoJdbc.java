package ru.sevryukov.learningspringdb.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.dao.AuthorDao;
import ru.sevryukov.learningspringdb.dao.mappers.AuthorMapper;
import ru.sevryukov.learningspringdb.model.Author;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(Author author) {
        var map = AuthorMapper.getMapFromAuthor(author);
        var sql = "insert into author (first_name, last_name) values (:first_name, :last_name)";
        namedParameterJdbcOperations.update(sql, map);
    }

    @Override
    public List<Author> getAll() {
        var sql = "select id, first_name, last_name from author";
        return namedParameterJdbcOperations.query(sql, new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        var params = Collections.singletonMap("id", id);
        var sql = "select id, first_name, last_name from author where id = :id";
        return namedParameterJdbcOperations.queryForObject(sql, params, new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);
        namedParameterJdbcOperations.update("delete from author where id = :id", params);
    }


}
