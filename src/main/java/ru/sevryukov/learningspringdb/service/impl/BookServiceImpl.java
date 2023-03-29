package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.repository.BookRepository;
import ru.sevryukov.learningspringdb.service.BookService;

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
    public List<Book> getAll(PageRequest pageRequest) {
        return bookRepo.findAll(pageRequest).stream().toList();
    }

    @Override
    @Transactional
    public String deleteBook(long id) {
        try {
            bookRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            return "No book found with id: " + id;
        }
        return String.format("Book with id %s was successfully removed", id);
    }

}
