package ru.sevryukov.learningspringdb.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Book {
    private final long id;
    private final String name;
    private final List<String> authorNames;
    private final List<String> genreNames;
}
