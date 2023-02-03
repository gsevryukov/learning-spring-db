package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.service.GenreService;
import ru.sevryukov.learningspringdb.service.GenreShellService;
import ru.sevryukov.learningspringdb.service.UserAskService;

@Service
@RequiredArgsConstructor
public class GenreShellServiceImpl implements GenreShellService {

    public static final String GENRE_HEADER = "ID\tGenre name";
    public static final String ENTER_A_VALID_GENRE_ID = "Enter a valid genre id!";
    private final UserAskService userAskService;
    private final GenreService genreService;

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
            var id = Long.parseLong(answer);
            genreService.deleteGenre(id);
            System.out.println("Genre with id " + id + " was successfully removed");
        } catch (Exception ex) {
            System.out.println(ENTER_A_VALID_GENRE_ID);
            removeGenre();
        }
    }

    private void printGenres() {
        System.out.println(GENRE_HEADER);
        genreService.getAll().forEach(v -> System.out.println(v.getId() + "\t" + v.getName()));
    }

}
