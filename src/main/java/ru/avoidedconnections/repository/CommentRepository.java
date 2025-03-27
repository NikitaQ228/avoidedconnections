package ru.avoidedconnections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.model.Story;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByStoryIdOrderByDateDesc(Long storyId);
}
