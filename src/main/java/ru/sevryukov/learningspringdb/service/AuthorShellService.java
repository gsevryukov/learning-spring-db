package ru.sevryukov.learningspringdb.service;

public interface AuthorShellService {

    String addAuthor(String firstName, String lastName);

    String getAuthor(long id);

    String getAllAuthors(int page, int size);

    String removeAuthor(long id);

}
