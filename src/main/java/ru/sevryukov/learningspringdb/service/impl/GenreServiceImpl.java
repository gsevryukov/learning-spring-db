package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.repository.GenreRepository;
import ru.sevryukov.learningspringdb.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepo;

    @Override
    @Transactional
    public Genre addGenre(String name) {
        return genreRepo.save(new Genre(name));
    }

    @Override
    public Genre getById(long id) {
        return Optional.of(genreRepo.findById(id)).get().orElse(null);
    }

    @Override
    public List<Genre> getAll(PageRequest pageRequest) {
        return genreRepo.findAll(pageRequest).stream().toList();
    }


    @Override
    public List<Genre> getAllByIds(List<Long> ids) {
        return genreRepo.findAllByIdIn(ids);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        try {
            genreRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            return String.format("No genre found with id %s", id);
        }
        return "Genre removed";
    }

}
