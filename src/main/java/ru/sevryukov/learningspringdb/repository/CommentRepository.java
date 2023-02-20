package ru.sevryukov.learningspringdb.repository;

import ru.sevryukov.learningspringdb.model.Comment;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    void removeComment(Comment comment);
}
