package ru.sevryukov.learningspringdb.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class BookEntity {
    private final long id;
    private final String bookName;
    private final List<Long> authorIds;
    private final List<Long> genreIds;

}
