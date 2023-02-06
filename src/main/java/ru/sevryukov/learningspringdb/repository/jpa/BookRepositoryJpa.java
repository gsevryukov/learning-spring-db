package ru.sevryukov.learningspringdb.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(manager.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        var query = manager.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public void removeBook(Book book) {
        manager.remove(book);
    }
}
