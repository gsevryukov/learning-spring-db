package ru.sevryukov.learningspringdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Book {
    private final long id;
    private final String name;
    private final List<String> authorNames;
    private final List<String> genreNames;
}
