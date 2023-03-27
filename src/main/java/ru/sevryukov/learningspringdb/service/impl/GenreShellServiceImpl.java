package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.GenreService;
import ru.sevryukov.learningspringdb.service.GenreShellService;
import ru.sevryukov.learningspringdb.service.OutputService;
import ru.sevryukov.learningspringdb.service.UserAskService;

import javax.persistence.EntityNotFoundException;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class GenreShellServiceImpl implements GenreShellService {

    private final GenreService genreService;
    private final OutputService outputService;
    private final UserAskService userAskService;
    public static final String GENRE_HEADER = "ID\tGenre name";
    public static final String ENTER_A_VALID_GENRE_ID = "Enter a valid genre id!";

    @Override
    public void addGenre() {
        var name = userAskService.askUser("Enter genre name...");
        genreService.addGenre(name);
    }

    @Override
    public void printGenre() {
        var answer = userAskService.askUser("Enter genre id...");
        if (answer.equals("exit")) {
            return;
        }
        try {
            var id = Long.parseLong(answer);
            var genre = genreService.getById(id);
            if (isNull(genre)) {
                throw new EntityNotFoundException(String.format("No genre wit id %s found.", id));
            }
            outputService.printOutput(GENRE_HEADER
                    + "\n" + genre.getId() + "\t" + genre.getName());
        } catch (Exception ex) {
            outputService.printOutput(ENTER_A_VALID_GENRE_ID);
            printGenre();
        }
    }

    @Override
    public void listAllGenres() {
        printGenres();
    }

    @Override
    public void removeGenre() {
        printGenres();
        var answer = userAskService.askUser("\nEnter genre id to remove:");
        try {
            var id = Long.parseLong(answer);
            genreService.deleteById(id);
            outputService.printOutput("Genre with id " + answer + " was successfully removed");
        } catch (Exception ex) {
            outputService.printOutput(ENTER_A_VALID_GENRE_ID);
            removeGenre();
        }
    }

    private void printGenres() {
        outputService.printOutput(GENRE_HEADER);
        genreService.getAll().forEach(v -> outputService.printOutput(v.getId() + "\t" + v.getName()));
    }

}
