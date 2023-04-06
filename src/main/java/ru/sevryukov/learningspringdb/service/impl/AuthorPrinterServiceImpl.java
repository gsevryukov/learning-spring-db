package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.model.Author;
import ru.sevryukov.learningspringdb.service.AuthorPrinterService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorPrinterServiceImpl implements AuthorPrinterService {

    public static final String AUTHOR_HEADER = "ID\tAuthor full name";

    @Override
    public String getAuthorsString(List<Author> authorList) {
        var idsAndNames = authorList
                .stream()
                .map(Author::toString)
                .toList();
        var sb = new StringBuilder(AUTHOR_HEADER);
        idsAndNames.forEach(v -> sb.append("\n").append(v));
        return sb.toString();
    }

    @Override
    public String getAuthorString(Author author) {
        return AUTHOR_HEADER + "\n" + author.toString();
    }

}
