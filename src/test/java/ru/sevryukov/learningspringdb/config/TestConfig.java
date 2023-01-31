package ru.sevryukov.learningspringdb.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.sevryukov.learningspringdb.dao.jdbc.BookDaoJdbc;
import ru.sevryukov.learningspringdb.dao.mappers.BookMapper;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.BookService;
import ru.sevryukov.learningspringdb.service.GenreService;
import ru.sevryukov.learningspringdb.service.impl.BookConverter;
import ru.sevryukov.learningspringdb.service.impl.BookServiceImpl;

import javax.sql.DataSource;

@Configuration
public class TestConfig {

    @MockBean
    GenreService genreService;

    @MockBean
    AuthorService authorService;

    @Bean
    @Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("/schema.sql")
                .addScript("/test-data.sql")
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public BookService bookService() {
        return new BookServiceImpl(bookDaoJdbc(), bookConverter());
    }

    @Bean
    public BookMapper bookMapper() {
        return new BookMapper();
    }

    @Bean
    public BookConverter bookConverter() {
        return new BookConverter(genreService, authorService);
    }

    @Bean
    public BookDaoJdbc bookDaoJdbc() {
        return new BookDaoJdbc(namedParameterJdbcTemplate(), bookMapper());
    }
}
