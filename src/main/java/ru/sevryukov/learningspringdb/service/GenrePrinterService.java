package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Genre;

import java.util.List;

public interface GenrePrinterService {

    String getGenresString(List<Genre> genreList);

    String getGenrerString(Genre genre);
}
