package ru.sevryukov.learningspringdb.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class Author {
    private final long id;
    private final String firstName;
    private final String lastname;

}
