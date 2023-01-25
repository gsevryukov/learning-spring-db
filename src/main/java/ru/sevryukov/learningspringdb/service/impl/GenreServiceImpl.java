package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.dao.jdbc.GenreDaoJdbc;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDaoJdbc genreRepo;

    @Override
    public void addGenre(String name) {
        genreRepo.insert(name);
    }

    @Override
    public Genre getById(long id) {
        return genreRepo.getById(id);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepo.getAll();
    }

    @Override
    public void deleteGenre(long id) {
        genreRepo.deleteById(id);
    }

}
