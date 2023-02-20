package ru.sevryukov.learningspringdb.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.repository.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager manager;

    @Override
    public Author save(Author author) {
        if (author.getId() <= 0) {
            manager.persist(author);
            return author;
        } else {
            return manager.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(manager.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        var query = manager.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public List<Author> findAllByIds(List<Long> ids) {
        var query = manager.createQuery("select a from Author a where a.id in :ids", Author.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public void removeAuthor(Author author) {
        manager.remove(author);
    }
}
