package ru.avoidedconnections.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.StoryResponse;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.services.StoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/story")
public class StoryController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/{storyId}")
    public StoryResponse storyInfo(@PathVariable(name = "storyId") Long storyId) {
        return storyService.getStoryById(storyId);
    }

    @GetMapping("/{storyId}/comment")
    public List<Comment> storyComment(@PathVariable(name = "storyId") Long storyId) {
        return null;
    }

    @PostMapping("/{storyId}/addComment")
    public void storyAddComment(@PathVariable Long storyId,
                                @RequestBody String commentText) {

    }

    @DeleteMapping("/{storyId}/delete")
    public void storyDelete(@PathVariable(name = "storyId") Long storyId) {

    }
}
