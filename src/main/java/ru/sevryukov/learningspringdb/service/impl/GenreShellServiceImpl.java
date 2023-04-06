package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.GenrePrinterService;
import ru.sevryukov.learningspringdb.service.GenreService;
import ru.sevryukov.learningspringdb.service.GenreShellService;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class GenreShellServiceImpl implements GenreShellService {

    private final GenreService genreService;
    private final GenrePrinterService genrePrinterService;

    @Override
    public String addGenre(String name) {
        genreService.addGenre(name);
        return "Genre added";
    }

    @Override
    public String getGenre(long id) {
        var genre = genreService.getById(id);
        if (isNull(genre)) {
            return (String.format("No genre with id %s found.", id));
        }
        return genrePrinterService.getGenrerString(genre);
    }

    @Override
    public String listAllGenres(int page, int size) {
        var p = PageRequest.of(page, size);
        var genres = genreService.getAll(p);
        if (genres.isEmpty()) {
            return "No genres found.";
        }
        return genrePrinterService.getGenresString(genres);
    }

    @Override
    public String removeGenre(long id) {
        return genreService.deleteById(id);
    }
}
