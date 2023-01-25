package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.dao.jdbc.AuthorDaoJdbc;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDaoJdbc authorRepo;

    @Override
    public void addAuthor(String firstName, String lastName) {
        authorRepo.insert(firstName, lastName);
    }

    @Override
    public void deleteAuthor(long id) {
        authorRepo.deleteById(id);
    }

    @Override
    public Author getById(long id) {
        return authorRepo.getById(id);
    }

    @Override
    public List<Author> getAll() {
        return authorRepo.getAll();
    }
}
