package ru.sevryukov.learningspringdb.service;

import java.util.List;

public interface BookAddService {

    String addBook(String bookName, List<Long> enteredAuthorIds, List<Long> enteredGenreIds);

}
