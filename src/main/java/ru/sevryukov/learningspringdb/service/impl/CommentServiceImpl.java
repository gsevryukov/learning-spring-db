package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.repository.CommentRepository;
import ru.sevryukov.learningspringdb.service.CommentService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    @Transactional
    public String deleteComment(long id) {
        try {
            commentRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            return String.format("No comment found with id %s", id);
        }
        return "Comment removed.";
    }

}
