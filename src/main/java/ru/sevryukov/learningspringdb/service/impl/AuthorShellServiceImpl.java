package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.AuthorPrinterService;
import ru.sevryukov.learningspringdb.service.AuthorService;
import ru.sevryukov.learningspringdb.service.AuthorShellService;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class AuthorShellServiceImpl implements AuthorShellService {

    private final AuthorService authorService;
    private final AuthorPrinterService authorPrinterService;

    @Override
    public String addAuthor(String firstName, String lastName) {
        authorService.addAuthor(firstName, lastName);
        return "Author added.";
    }

    @Override
    public String getAuthor(long id) {
        var author = authorService.getById(id);
        if (isNull(author)) {
            return String.format("No author with id %s found.", id);
        }
        return authorPrinterService.getAuthorString(author);
    }

    @Override
    public String getAllAuthors(int page, int size) {
        var p = PageRequest.of(page, size);
        var authors = authorService.getAll(p);
        if (authors.isEmpty()) {
            return "No authors found.";
        }
        return authorPrinterService.getAuthorsString(authors);
    }

    @Override
    public String removeAuthor(long id) {
       return authorService.deleteAuthor(id);
    }

}
