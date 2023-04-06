package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Author;

import java.util.List;

public interface AuthorPrinterService {

    String getAuthorsString(List<Author> authorList);

    String getAuthorString(Author author);
}
