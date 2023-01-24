package ru.sevryukov.learningspringdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sevryukov.learningspringdb.dao.AuthorDao;
import ru.sevryukov.learningspringdb.dao.jdbc.BookDaoJdbc;
import ru.sevryukov.learningspringdb.model.Author;

import java.util.Arrays;

@SpringBootApplication
public class LearningSpringDbApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(LearningSpringDbApplication.class, args);
        var dao = ctx.getBean(AuthorDao.class);
        var author = dao.getById(1);
        System.out.println("Author first name is " + author.getFirstName());
        var newAuthor = new Author(2, "Фёдор", "Достоевский");
        dao.insert(newAuthor);
        var all = dao.getAll().toArray();
        System.out.println("All authors:\t" + Arrays.toString(all));
        var bDao = ctx.getBean(BookDaoJdbc.class);
        var allB = bDao.getAll().toArray();
        System.out.println("All books:\t" + Arrays.toString(allB));
//        var nB = new BookEntity(3, "Говнокнига", 1, 1);
//        bDao.insert(nB);
//        allB = bDao.getAll().toArray();
//        System.out.println("All books after add:\t" + Arrays.toString(allB));
//        bDao.deleteById(1);
    }

}
