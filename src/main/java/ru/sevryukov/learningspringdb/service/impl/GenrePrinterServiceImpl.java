package ru.sevryukov.learningspringdb.service.impl;

import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.service.GenrePrinterService;

import java.util.List;

@Service
public class GenrePrinterServiceImpl implements GenrePrinterService {

    public static final String GENRE_HEADER = "ID\tGenre name";

    @Override
    public String getGenresString(List<Genre> genreList) {
        var idsAndNames = genreList
                .stream()
                .map(Genre::toString)
                .toList();
        var sb = new StringBuilder(GENRE_HEADER);
        idsAndNames.forEach(v -> sb.append("\n").append(v));
        return sb.toString();
    }

    @Override
    public String getGenrerString(Genre genre) {
        return GENRE_HEADER + "\n" + genre.getId() + "\t" + genre.getName();
    }
}
