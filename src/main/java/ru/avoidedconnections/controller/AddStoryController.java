package ru.avoidedconnections.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.services.StoryService;

@RestController
@RequestMapping("/addStory")
public class AddStoryController {
    @Autowired
    StoryService storyService;

    @PostMapping("/new")
    public ResponseEntity<StoryDTO> createStory(@RequestBody StoryDTO storyDTO) {
        StoryDTO savedStory = storyService.addStory(storyDTO);
        return new ResponseEntity<>(savedStory, HttpStatus.CREATED);
    }

}
