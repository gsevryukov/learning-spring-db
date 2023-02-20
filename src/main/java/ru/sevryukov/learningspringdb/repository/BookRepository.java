package ru.sevryukov.learningspringdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevryukov.learningspringdb.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
