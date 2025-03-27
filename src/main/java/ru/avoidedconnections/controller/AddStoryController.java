package ru.avoidedconnections.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.StoryDTO;

@RestController
@RequestMapping("/addStory")
public class AddStoryController {

    @PostMapping("/new")
    public ResponseEntity<String> createStory(@RequestBody StoryDTO storyRequest) {
        return null;
    }

}
