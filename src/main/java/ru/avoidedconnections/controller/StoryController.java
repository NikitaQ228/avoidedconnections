package ru.avoidedconnections.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.CommentDTO;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.services.CommentService;
import ru.avoidedconnections.services.StoryService;
import ru.avoidedconnections.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    private final StoryService storyService;
    private final CommentService commentService;
    private final UserService userService;

    public StoryController(StoryService storyService, CommentService commentService, UserService userService) {
        this.storyService = storyService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/{storyId}")
    public StoryDTO getStoryInfo(@PathVariable(name = "storyId") Long storyId) {
        return new StoryDTO(storyService.getStoryById(storyId));
    }

    @GetMapping("/{storyId}/comment")
    public List<CommentDTO> getStoryComments(@PathVariable(name = "storyId") Long storyId) {
        return commentService.getCommentsByStoryId(storyId);
    }

    @PostMapping("/{storyId}/addComment")
    public ResponseEntity<CommentDTO> storyAddComment(@PathVariable Long storyId,
                                @RequestBody String commentText) {
        var savedComment = commentService.saveComment(commentText, storyId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{storyId}/delete")
    public StoryDTO storyDelete(@PathVariable(name = "storyId") Long storyId) {
        return storyService.deleteStory(storyId);
    }
}
