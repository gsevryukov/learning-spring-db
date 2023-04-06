package ru.sevryukov.learningspringdb.service;

import org.springframework.data.domain.PageRequest;
import ru.sevryukov.learningspringdb.model.Author;

import java.util.List;

public interface AuthorService {

    void addAuthor(String firstName, String lastName);

    Author getById(long id);

    List<Author> getAllByIds(List<Long> ids);

    List<Author> getAll(PageRequest pageRequest);

    String deleteAuthor(long id);
}
