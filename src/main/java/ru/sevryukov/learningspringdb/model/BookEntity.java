package ru.sevryukov.learningspringdb.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class BookEntity {
//    private long id;
    private final String bookName;
    private final List<Long> authorIds;
    private final List<Long> genreIds;

//    public BookEntity(String bookName, List<Long> authorIds, List<Long> genreIds) {
//        this.bookName = bookName;
//        this.authorIds = authorIds;
//        this.genreIds = genreIds;
//    }
//
//    public BookEntity(long id, String bookName, List<Long> authorIds, List<Long> genreIds) {
//        this.id = id;
//        this.bookName = bookName;
//        this.authorIds = authorIds;
//        this.genreIds = genreIds;
//    }
}
