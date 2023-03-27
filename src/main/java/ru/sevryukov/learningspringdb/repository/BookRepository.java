package ru.sevryukov.learningspringdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevryukov.learningspringdb.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByNameContainsAndCommentsIsNotNull(String name);
}
