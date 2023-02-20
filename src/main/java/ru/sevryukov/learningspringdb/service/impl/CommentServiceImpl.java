package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevryukov.learningspringdb.model.Comment;
import ru.sevryukov.learningspringdb.repository.CommentRepository;
import ru.sevryukov.learningspringdb.service.CommentService;

import javax.persistence.EntityNotFoundException;

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
    public void editComment(long id, String text) {
        commentRepo.findById(id).ifPresentOrElse(
                comment -> {
                    comment.setText(text);
                    commentRepo.save(comment);
                },
                () -> {
                    throw new EntityNotFoundException("No comment found with id: " + id);
                }
        );
    }

    @Override
    @Transactional
    public void deleteComment(long id) {
        try {
            commentRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("No comment found with id: " + id);
        } catch (Exception ex) {
            System.out.println("Comment delete error: " + ex);
        }
    }

}
