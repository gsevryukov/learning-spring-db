package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.Book;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.service.BookCommentService;
import ru.sevryukov.learningspringdb.service.CommentService;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final CommentService commentService;

    @Override
    public String addBookComment(Book book, String comment) {
        var comments = book.getComments();
        var c = commentService.saveComment(new Comment(comment));
        comments.add(c);
        return "Comment added.";
    }

    @Override
    public String editBookComment(Book book, long commentId, String text) {
        var commentOpt = book.getComments().stream().filter(c -> c.getId() == commentId).findFirst();
        if (commentOpt.isEmpty()) {
            return String.format("No comment with id %s found for book %s", commentId, book.getName());
        }
        commentOpt.get().setText(text);
        return "Comment updated.";
    }

    @Override
    @Transactional
    public String removeBookComment(Book book, long commentId) {
        var commentOpt = book.getComments().stream().filter(c -> c.getId() == commentId).findFirst();
        if (commentOpt.isEmpty()) {
            return String.format("No comment with id %s found for book %s", commentId, book.getName());
        }
        book.getComments().remove(commentOpt.get());
        return commentService.deleteComment(commentId);
    }

}
