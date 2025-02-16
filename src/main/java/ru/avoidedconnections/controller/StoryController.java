package ru.avoidedconnections.controller;

import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.model.Story;

@RestController
@RequestMapping("/story")
public class StoryController {
    @GetMapping("/{storyId}")
    public Story storyPage(@PathVariable(name = "storyId") Long storyId) {
        return null;
    }
}
