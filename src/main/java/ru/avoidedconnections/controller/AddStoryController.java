package ru.avoidedconnections.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.StoryRequest;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;

@RestController
@RequestMapping("/api/addStory")
public class AddStoryController {

    @PostMapping("/new")
    public ResponseEntity<String> createStory(@RequestBody StoryRequest storyRequest) {
        return null;
    }

}
