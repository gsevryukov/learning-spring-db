package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public void editBook(long id, String bookName) {
        bookRepo.updateById(id, bookName);
    }

    @Override
    public void deleteBook(long id) {
        bookRepo.deleteById(id);
    }

}
