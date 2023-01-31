package ru.sevryukov.learningspringdb.dao.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.BookEntity;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMapper implements RowMapper<BookEntity> {

    @Override
    public BookEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        var bookName = rs.getString("name");
        var genreIdStr = rs.getArray("genre_ids");
        var authorIdStr = rs.getArray("author_ids");
        return new BookEntity(id, bookName, getIdsFromStr(authorIdStr), getIdsFromStr(genreIdStr));
    }

    private List<Long> getIdsFromStr(Array listStr) {
        try {
            var arr = (Object[]) listStr.getArray();
            if (arr.length == 0) {
                return List.of();
            }
            return Arrays.stream(arr).map(Long.class::cast).toList();

        } catch (Exception ex) {
            System.out.println("Error while getting an ids array" + ex);
            return List.of();
        }

    }

}
