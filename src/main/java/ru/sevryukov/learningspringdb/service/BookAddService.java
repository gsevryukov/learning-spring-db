package ru.sevryukov.learningspringdb.service;

import java.util.List;

public interface BookAddService {

    void addBook(String bookName, List<Long> enteredAuthorIds, List<Long> enteredGenreIds);

}
