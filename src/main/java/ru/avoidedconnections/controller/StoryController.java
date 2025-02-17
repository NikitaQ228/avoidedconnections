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
    public Pair<Story, List<Comment>> storyPage(@PathVariable(name = "storyId") Long storyId) {
        return null;
    }
}
