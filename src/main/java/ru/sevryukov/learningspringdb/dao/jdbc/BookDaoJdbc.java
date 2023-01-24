package ru.sevryukov.learningspringdb.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.dao.BookDao;
import ru.sevryukov.learningspringdb.dao.mappers.BookMapper;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.BookEntity;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(BookEntity bookEntity) {
        var map = BookMapper.getMapFromBook(bookEntity);
        var sql = "insert into book(id, \"name\", author_id, genre_id) values (:id, :name, :author_id, :genre_id)";
        namedParameterJdbcOperations.update(sql, map);
    }

    @Override
    public List<Book> getAll() {
        var sql = "select b.id, " +
                "b.\"name\" as book_name, " +
                "(select array(select concat(a.first_name, ' ', a.last_name) from author a)) as full_names, " +
                "(select array(select g.\"name\" from genre g)) as genre_names, " +
                "from book b ";
        return namedParameterJdbcOperations.query(sql, new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);
        namedParameterJdbcOperations.update("delete from book where id = :id", params);
    }

}
