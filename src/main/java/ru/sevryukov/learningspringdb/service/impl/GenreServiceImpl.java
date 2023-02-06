package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.repository.GenreRepository;
import ru.sevryukov.learningspringdb.service.GenreService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepo;

    @Override
    public void addGenre(String name) {
        genreRepo.save(new Genre(0, name));
    }

    @Override
    public Genre getById(long id) {
        return Optional.of(genreRepo.findById(id)).get().orElse(null);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepo.findAll();
    }

    @Override
    public List<Genre> getAllByIds(List<Long> ids) {
        return genreRepo.findAllByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        genreRepo.findById(id).ifPresentOrElse(
                genreRepo::removeGenre,
                () -> {
                    throw new EntityNotFoundException("No genre found with id: " + id);
                }
        );

    }

}
