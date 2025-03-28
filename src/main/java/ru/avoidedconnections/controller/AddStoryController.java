package ru.avoidedconnections.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.services.StoryService;

@RestController
@RequestMapping("/addStory")
public class AddStoryController {
    private final StoryService storyService;

    public AddStoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @PostMapping("/new")
    public ResponseEntity<StoryDTO> createStory(@RequestBody StoryDTO storyDTO) {
        return new ResponseEntity<>(storyService.addStory(storyDTO), HttpStatus.CREATED);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        return storyService.uploadImage(image);
    }

}
