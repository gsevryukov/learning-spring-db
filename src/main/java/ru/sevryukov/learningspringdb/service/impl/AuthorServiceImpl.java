package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.repository.AuthorRepository;
import ru.sevryukov.learningspringdb.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepo;

    @Override
    public void addAuthor(String firstName, String lastName) {
        authorRepo.save(new Author(0, firstName, lastName));
    }

    @Override
    public Author getById(long id) {
        return Optional.of(authorRepo.findById(id)).get().orElse(null);
    }

    @Override
    public List<Author> getAll() {
        return authorRepo.findAll();
    }

    @Override
    public List<Author> getAllByIds(List<Long> ids) {
        return authorRepo.findAllByIds(ids);
    }

    @Override
    public void deleteAuthor(long id) {
        authorRepo.deleteById(id);
    }
}
