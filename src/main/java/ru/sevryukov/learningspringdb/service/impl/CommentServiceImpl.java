package ru.sevryukov.learningspringdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sevryukov.learningspringdb.repository.CommentRepository;
import ru.sevryukov.learningspringdb.service.CommentService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;

    @Override
    public void deleteComment(long id) {
        commentRepo.deleteById(id);
    }

}