package ru.sevryukov.learningspringdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.sevryukov.learningspringdb.repository.BookRepository;
import ru.sevryukov.learningspringdb.repository.GenreRepository;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.GenreService;
import ru.sevryukov.learningspringdb.service.OutputService;
import ru.sevryukov.learningspringdb.service.impl.BookServiceImpl;
import ru.sevryukov.learningspringdb.service.impl.GenreServiceImpl;
import ru.sevryukov.learningspringdb.service.impl.OutputServiceImpl;

@TestConfiguration
public class ServiceTestConfig {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Bean
    public OutputService outputService() {
        return new OutputServiceImpl();
    }

    @Bean
    public GenreService genreService() {
        return new GenreServiceImpl(genreRepository, outputService());
    }

    @Bean
    public BookService bookService() {
        return new BookServiceImpl(bookRepository, outputService());
    }
}
