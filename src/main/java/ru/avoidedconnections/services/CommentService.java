package ru.avoidedconnections.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avoidedconnections.dto.CommentDTO;
import ru.avoidedconnections.dto.UserDTO;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.repository.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDTO> getCommentsByStoryId(Long id) {
        return Collections.unmodifiableList(
                commentRepository.findByStoryIdOrderByDateDesc(id).stream()
                        .map(CommentDTO::new)
                        .collect(Collectors.toList())
        );
    }

}
