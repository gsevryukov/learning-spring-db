package ru.sevryukov.learningspringdb.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.dao.BookDao;
import ru.sevryukov.learningspringdb.dao.mappers.BookMapper;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.BookEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private static final String BASE_SELECT = "select b.id, " +
            "b.\"name\" as book_name, " +
            "(select array(select concat(a.first_name, ' ', a.last_name) from author a)) as full_names, " +
            "(select array(select g.\"name\" from genre g)) as genre_names " +
            "from book b ";

    @Override
    public void insert(BookEntity bookEntity) {
        var params = BookMapper.getMapFromBook(bookEntity);
        var sql = "insert into book(\"name\", author_ids, genre_ids) values (:name, :author_ids, :genre_ids)";
        namedParameterJdbcOperations.update(sql, params);
    }

    @Override
    public Book getById(long id) {
        var params = Collections.singletonMap("id", id);
        var sql = BASE_SELECT + "where b.id = :id";
        return namedParameterJdbcOperations.queryForObject(sql, params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(BASE_SELECT, new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);
        namedParameterJdbcOperations.update("delete from book where id = :id", params);
    }

}
