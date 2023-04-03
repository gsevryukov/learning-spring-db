package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void addAuthor(String firstName, String lastName) {
        authorRepo.save(new Author(firstName, lastName));
    }

    @Override
    public Author getById(long id) {
        return Optional.of(authorRepo.findById(id)).get().orElse(null);
    }

    @Override
    public List<Author> getAllByIds(List<Long> ids) {
        return authorRepo.findAllByIdIn(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll(PageRequest pageRequest) {
        return authorRepo.findAll(pageRequest).stream().toList();
    }

    @Override
    @Transactional
    public String deleteAuthor(long id) {
        try {
            authorRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            return String.format("No author with this id %s", id);
        }
        return "Author removed";
    }
}
