package ru.sevryukov.learningspringdb.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.repository.CommentRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    private final EntityManager manager;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            manager.persist(comment);
            return comment;
        } else {
            return manager.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(manager.find(Comment.class, id));
    }

    @Override
    public void removeComment(Comment comment) {
        manager.remove(comment);
    }
}
