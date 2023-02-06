package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.repository.CommentRepository;
import ru.sevryukov.learningspringdb.service.CommentService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;

    @Override
    public void deleteComment(long id) {
        commentRepo.findById(id).ifPresentOrElse(
                commentRepo::removeComment,
                () -> {
                    throw new EntityNotFoundException("No comment found with id: " + id);
                }
        );
    }

}
