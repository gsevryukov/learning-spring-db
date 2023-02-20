package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.EntityService;
import ru.sevryukov.learningspringdb.service.GenreShellService;
import ru.sevryukov.learningspringdb.service.UserAskService;

@Service
@RequiredArgsConstructor
public class GenreShellServiceImpl implements GenreShellService {

    public static final String GENRE_HEADER = "ID\tGenre name";
    public static final String ENTER_A_VALID_GENRE_ID = "Enter a valid genre id!";
    private final UserAskService userAskService;
    private final EntityService entityService;

    @Override
    public void addGenre() {
        var name = userAskService.askUser("Enter genre name...");
        entityService.addGenre(name);
    }

    @Override
    public void printGenre() {
        var answer = userAskService.askUser("Enter genre id...");
        if (answer.equals("exit")) {
            return;
        }
        try {
            var genre = entityService.getGenreById(answer);
            System.out.println(GENRE_HEADER
                    + "\n" + genre.getId() + "\t" + genre.getName());
        } catch (Exception ex) {
            System.out.println(ENTER_A_VALID_GENRE_ID);
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
            entityService.removeGenre(answer);
            System.out.println("Genre with id " + answer + " was successfully removed");
        } catch (Exception ex) {
            System.out.println(ENTER_A_VALID_GENRE_ID);
            removeGenre();
        }
    }

    private void printGenres() {
        System.out.println(GENRE_HEADER);
        entityService.getAllGenres().forEach(v -> System.out.println(v.getId() + "\t" + v.getName()));
    }

}
