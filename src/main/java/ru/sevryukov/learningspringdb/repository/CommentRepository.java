package ru.sevryukov.learningspringdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevryukov.learningspringdb.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
