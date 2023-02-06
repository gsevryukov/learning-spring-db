package ru.sevryukov.learningspringdb.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.model.Genre;
import ru.sevryukov.learningspringdb.repository.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager manager;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() <= 0) {
            manager.persist(genre);
            return genre;
        } else {
            return manager.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(manager.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        var query = manager.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public List<Genre> findAllByIds(List<Long> ids) {
        var query = manager.createQuery("select g from Genre g where g.id in :ids", Genre.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public void removeGenre(Genre genre) {
        manager.remove(genre);
    }
}
