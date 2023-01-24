package ru.sevryukov.learningspringdb.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookEntity {
    private final long id;
    private final String bookName;
    private final long authorId;
    private final long genreId;
}
