package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.dao.jdbc.BookDaoJdbc;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.BookEntity;
import ru.sevryukov.learningspringdb.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDaoJdbc bookRepo;

    @Override
    public void addBook(String bookName, List<Long> authorIds, List<Long> genreIds) {
        var bookEnt = new BookEntity(bookName, authorIds, genreIds);
        bookRepo.insert(bookEnt);
    }

    @Override
    public Book getById(long id) {
        return bookRepo.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookRepo.getAll();
    }

    @Override
    public void editBook(long id, String bookName, List<Long> authorIds, List<Long> genreIds) {
        bookRepo.editBook(id, bookName, authorIds, genreIds);
    }

    @Override
    public void deleteBook(long id) {
        bookRepo.deleteById(id);
    }

}
