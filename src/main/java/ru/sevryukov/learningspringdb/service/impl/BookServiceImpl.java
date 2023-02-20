package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.repository.BookRepository;
import ru.sevryukov.learningspringdb.service.BookService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;

    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Book getById(long id) {
        return Optional.of(bookRepo.findById(id)).get().orElse(null);
    }

    @Override
    public List<Book> getAll() {
        return bookRepo.findAll();

    }

    @Override
    @Transactional
    public void editBook(long id, String bookName) {
        bookRepo.findById(id).ifPresentOrElse(
                book -> {
                    book.setName(bookName);
                    bookRepo.save(book);
                },
                () -> getException(id)
        );
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        try {
            bookRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            getException(id);
        } catch (Exception ex) {
            System.out.println("Book delete error: " + ex);
        }
    }

    private void getException(long id) {
        throw new EntityNotFoundException("No book found with id: " + id);
    }

}
