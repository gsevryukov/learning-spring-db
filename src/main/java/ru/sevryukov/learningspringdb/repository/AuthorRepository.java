package ru.sevryukov.learningspringdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevryukov.learningspringdb.model.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByIdIn(List<Long> ids);
}
