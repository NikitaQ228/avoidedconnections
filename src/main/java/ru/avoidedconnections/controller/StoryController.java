package ru.avoidedconnections.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.CommentDTO;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.services.CommentService;
import ru.avoidedconnections.services.StoryService;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/{storyId}")
    public StoryDTO getStoryInfo(@PathVariable(name = "storyId") Long storyId) {
        return storyService.getStoryById(storyId);
    }

    @GetMapping("/{storyId}/comment")
    public List<CommentDTO> getStoryComments(@PathVariable(name = "storyId") Long storyId) {
        return commentService.getCommentsByStoryId(storyId);
    }

    @PostMapping("/{storyId}/addComment")
    public void storyAddComment(@PathVariable Long storyId,
                                @RequestBody String commentText) {

    }

    @DeleteMapping("/{storyId}/delete")
    public void storyDelete(@PathVariable(name = "storyId") Long storyId) {

    }
}
