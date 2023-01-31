package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.dao.jdbc.BookDaoJdbc;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDaoJdbc bookRepo;

    private final BookConverter bookConverter;

    @Override
    public void addBook(String bookName, List<Long> authorIds, List<Long> genreIds) {
        bookRepo.insert(bookName, authorIds, genreIds);
    }

    @Override
    public Book getById(long id) {
        var bookEnt = bookRepo.getById(id);
        return bookConverter.getBookFromBookEntity(bookEnt);
    }

    @Override
    public List<Book> getAll() {
        var entities = bookRepo.getAll();
        return entities.stream().map(bookConverter::getBookFromBookEntity).toList();

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
