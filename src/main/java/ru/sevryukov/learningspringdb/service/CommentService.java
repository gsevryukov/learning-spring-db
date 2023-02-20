package ru.sevryukov.learningspringdb.service;

import ru.sevryukov.learningspringdb.model.Comment;

public interface CommentService {
    Comment saveComment(Comment comment);

    void editComment(long id, String text);

    void deleteComment(long id);
}
