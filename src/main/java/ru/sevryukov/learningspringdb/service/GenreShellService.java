package ru.sevryukov.learningspringdb.service;

public interface GenreShellService {

    String addGenre(String name);

    String getGenre(long id);

    String listAllGenres(int page, int size);

    String removeGenre(long id);

}
