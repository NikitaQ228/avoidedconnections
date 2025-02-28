package ru.avoidedconnections.controller;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.model.Story;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    @GetMapping("/{storyId}")
    public Story storyPage(@PathVariable(name = "storyId") Long storyId) {
        return null;
    }

    @GetMapping("/{storyId}/comment")
    public List<Comment> storyComment(@PathVariable(name = "storyId") Long storyId) {
        return null;
    }

    @PostMapping("/{storyId}/addComment")
    public void storyAddComment(@PathVariable Long storyId,
                                @RequestBody String commentText) {

    }

    //todo удаление статьи?
}
