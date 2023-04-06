package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Comment;

public interface CommentService {
    Comment saveComment(Comment comment);

    String deleteComment(long id);
}
