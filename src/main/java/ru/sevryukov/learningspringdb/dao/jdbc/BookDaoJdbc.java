package ru.sevryukov.learningspringdb.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.dao.BookDao;
import ru.sevryukov.learningspringdb.dao.mappers.BookMapper;
import ru.sevryukov.learningspringdb.model.BookEntity;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final BookMapper bookMapper;

    private static final String BASE_SELECT = "select id, \"name\", author_ids, genre_ids from book b ";

    @Override
    public void insert(String name, List<Long> authorIds, List<Long> genreIds) {
        var params = Map.of(
                "name", name,
                "author_ids", authorIds.toArray(),
                "genre_ids", genreIds.toArray()
        );
        var sql = "insert into book(\"name\", author_ids, genre_ids) values (:name, :author_ids, :genre_ids)";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public BookEntity getById(long id) {
        var params = Map.of("id", id);
        var sql = BASE_SELECT + "where b.id = :id";
        return namedParameterJdbcTemplate.queryForObject(sql, params, bookMapper);
    }

    @Override
    public List<BookEntity> getAll() {
        return namedParameterJdbcTemplate.query(BASE_SELECT, bookMapper);
    }

    @Override
    public void editBook(long id, String name, List<Long> authorIds, List<Long> genreIds) {
        var params = Map.of(
                "id", id,
                "name", name,
                "author_ids", authorIds.toArray(),
                "genre_ids", genreIds.toArray()
        );
        var sql = "update book " +
                "set \"name\" = :name, " +
                "author_ids = :author_ids, " +
                "genre_ids = :genre_ids" +
                " where id = :id";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);
        namedParameterJdbcTemplate.update("delete from book where id = :id", params);
    }

}
