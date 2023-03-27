package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.repository.GenreRepository;
import ru.sevryukov.learningspringdb.service.GenreService;
import ru.sevryukov.learningspringdb.service.OutputService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepo;
    private final OutputService outputService;

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
    public List<Genre> getAll() {
        return genreRepo.findAll();
    }

    @Override
    public List<Genre> getAllByIds(List<Long> ids) {
        return genreRepo.findAllByIdIn(ids);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        try {
            genreRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("No genre found with id: " + id);
        } catch (Exception ex) {
            outputService.printOutput("Genre delete error: " + ex);
        }
    }

}
