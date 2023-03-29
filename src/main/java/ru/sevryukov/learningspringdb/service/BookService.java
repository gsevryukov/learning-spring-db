package ru.sevryukov.learningspringdb.service;

import org.springframework.data.domain.PageRequest;
import ru.sevryukov.learningspringdb.model.Book;

import java.util.List;

public interface BookService {

    Book saveBook(Book book);

    Book getById(long id);

    List<Book> getAll(PageRequest pageRequest);

    String deleteBook(long id);

}
