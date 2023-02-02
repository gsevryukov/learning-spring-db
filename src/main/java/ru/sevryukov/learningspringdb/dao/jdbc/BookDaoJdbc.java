package ru.sevryukov.learningspringdb.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.dao.BookDao;
import ru.sevryukov.learningspringdb.dao.mappers.BookMapper;
import ru.sevryukov.learningspringdb.model.Book;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final BookMapper bookMapper;

    private static final String BASE_SELECT = "select b.id, b.\"name\", " +
            "array_agg(distinct concat(a.first_name, ' ', a.last_name)) as author_names, " +
            "array_agg(distinct concat(g.\"name\", '')) as genre_names " +
            "from book b " +
            "join genre g on array_contains(b.genre_ids, g.id) " +
            "join author a on array_contains(b.author_ids, a.id)";

    @Override
    public void insert(String name, List<Long> authorIds, List<Long> genreIds) {
        var params = Map.of(
                "naming", name,
                "author_ids", authorIds.toArray(),
                "genre_ids", genreIds.toArray()
        );
        var sql = "insert into book(\"name\", author_ids, genre_ids) values (:name, :author_ids, :genre_ids)";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Book getById(long id) {
        var params = Map.of("id", id);
        var sql = BASE_SELECT + " where b.id = :id group by b.id";
        return namedParameterJdbcTemplate.queryForObject(sql, params, bookMapper);
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcTemplate.query(BASE_SELECT + " group by b.id", bookMapper);
    }

    @Override
    public void editBook(long id, String name, List<Long> authorIds, List<Long> genreIds) {
        var params = Map.of(
                "id", id,
                "naming", name,
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
