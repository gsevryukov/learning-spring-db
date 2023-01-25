package ru.sevryukov.learningspringdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {
    private long id;
    private String name;
    private final List<String> authorNames;
    private final List<String> genreNames;
}
