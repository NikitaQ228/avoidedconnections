package ru.avoidedconnections.services;

import org.springframework.stereotype.Service;
import ru.avoidedconnections.dto.CommentDTO;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.repository.CommentRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final StoryService storyService;

    public CommentService(CommentRepository commentRepository, UserService userService, StoryService storyService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.storyService = storyService;
    }

    public CommentDTO saveComment(String commentText, Long storyId) {
        var storyObj = storyService.getStoryById(storyId);
        Comment comment = new Comment(
                userService.getCurrentUser(),
                storyObj,
                new Date(),
                commentText
        );
        commentRepository.save(comment);
        return new CommentDTO(comment);
    }

    public List<CommentDTO> getCommentsByStoryId(Long id) {
        return Collections.unmodifiableList(
                commentRepository.findByStoryIdOrderByDateAsc(id).stream()
                        .map(CommentDTO::new)
                        .collect(Collectors.toList())
        );
    }

}
