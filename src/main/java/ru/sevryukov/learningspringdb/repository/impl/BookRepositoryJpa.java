package ru.sevryukov.learningspringdb.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager manager;

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            manager.persist(book);
            return book;
        } else {
            return manager.merge(book);
        }
    }

    @Override
    @Transactional
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(manager.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        var query = manager.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void updateById(long id, String name) {
        var query = manager.createQuery("update Book b " +
                "set b.name = :name " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = manager.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
